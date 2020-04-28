package com.sample.repository

import com.sample.User
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.into
import reactor.core.publisher.Mono

class UserRepository(private val client: DatabaseClient) {

	fun count() = client.execute().sql("SELECT COUNT(*) FROM users").asType<Long>().fetch().one()

	fun findAll() = client.select().from("users").asType<User>().fetch().all()

	fun findOne(id: String) = 
		client.execute().sql("SELECT * FROM users WHERE login = :login").bind("login", id).asType<User>().fetch().one()

	fun findLastOne() = 
		client.execute().sql("SELECT * FROM users ORDER BY id DESC LIMIT 1").asType<User>().fetch().one().repeat().distinctUntilChanged()

	fun deleteAll() = client.execute().sql("DELETE FROM users").fetch().one().then()

	fun save(user: User): Mono<Void> = 
		count().flatMap { user.id = it.toInt() client.insert().into<User>().table("users").using(user).then() }

	fun init() {
		client.execute().sql("CREATE TABLE IF NOT EXISTS users (id int PRIMARY KEY, login varchar, firstname varchar, lastname varchar, phonenumber varchar, birthdate varchar, description varchar, avatar varchar);").then()
			.then(deleteAll())
			.then(save(User(0, "lmorda", "Lou", "Morda", "858-323-4431", "1981-01-11", "Daddy", 
					"https://randomuser.me/api/portraits/lego/0.jpg")))
			.then(save(User(1, "kmorda", "Kate", "Morda","858-323-4432", "1982-02-22", "Mommy", 
					"https://randomuser.me/api/portraits/lego/1.jpg")))
			.then(save(User(2, "lillym", "Lilly", "Morda","858-323-4433", "2014-03-03","Baby", 
					"https://randomuser.me/api/portraits/lego/2.jpg")))
			.then(save(User(3, "mmorda", "Mary", "Morda","858-323-4434", "1945-04-04","Grandmom", 
					"https://randomuser.me/api/portraits/lego/3.jpg")))
			.block()
	}

}
