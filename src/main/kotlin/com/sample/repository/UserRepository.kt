package com.sample.repository

import com.sample.User
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono

class UserRepository(private val client: DatabaseClient) {

	fun count() =
		client.sql("SELECT COUNT(login) FROM users")
			.map { row -> (row.get(0) as Long).toInt() }
			.first()

	fun findAll() =
		client.sql("SELECT * FROM users")
			.map { row ->
				User(
					login = row.get("login", String::class.java)!!,
					firstname = row.get("firstname", String::class.java)!!,
					lastname = row.get("lastname", String::class.java)!!,
					phonenumber = row.get("phonenumber", String::class.java)!!,
					birthdate = row.get("birthdate", String::class.java)!!,
					avatar = row.get("avatar", String::class.java)!!
				)
			}
			.all()

	fun findOne(login: String?) =
		login?.let {
			client.sql("SELECT * FROM users WHERE login = :login")
				.bind("login", it)
				.map { row ->
					User(
						login = row.get("login", String::class.java)!!,
						firstname = row.get("firstname", String::class.java)!!,
						lastname = row.get("lastname", String::class.java)!!,
						phonenumber = row.get("phonenumber", String::class.java)!!,
						birthdate = row.get("birthdate", String::class.java)!!,
						avatar = row.get("avatar", String::class.java)!!
					)
				}.first()
		} ?: Mono.empty()

	fun insert(user: User) =
		client.sql("INSERT INTO users(login, firstname, lastname, phonenumber, birthdate, avatar) " +
				"values(:login, :firstname, :lastname, :phonenumber, :birthdate, :avatar)")
			.bind("login", user.login)
			.bind("firstname", user.firstname)
			.bind("lastname", user.lastname)
			.bind("phonenumber", user.phonenumber)
			.bind("birthdate", user.birthdate)
			.bind("avatar", user.avatar)
			.then()
}