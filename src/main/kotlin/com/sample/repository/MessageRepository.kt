package com.sample.repository

import com.sample.Message
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.into
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class MessageRepository(private val client: DatabaseClient) {

	fun count() = client.execute("SELECT COUNT(*) FROM messages").asType<Long>().fetch().one()

	fun findAll() = client.select().from("messages").asType<Message>().fetch().all()

	fun findOne(id: String) = 
		client.execute("SELECT * FROM messages WHERE id = :id").bind("id", id).asType<Message>().fetch().one()

	fun findLastOne(): Flux<Message> = 
		client.execute("SELECT * FROM messages ORDER BY id DESC LIMIT 1").asType<Message>().fetch().one()
			.repeat().distinctUntilChanged()
	
	fun save(message: Message): Mono<Void> =
		count().flatMap {
			message.id = it.toInt()
			client.insert().into<Message>().table("messages").using(message).then()
		}

	fun deleteAll() = client.execute("DELETE FROM messages").fetch().one().then()

	fun init() {
		client.execute("CREATE TABLE IF NOT EXISTS messages (id int PRIMARY KEY, created_timestamp varchar, recipient_login varchar, sender_login varchar, message varchar);").then()
			.then(deleteAll())
			.then(save(Message(0, "2019-01-11T11:22:33Z", "lmorda", "kmorda", "Hi Lou")))
			.then(save(Message(1, "2019-02-12T12:33:44Z", "kmorda", "lmorda", "Hi Kate")))
			.then(save(Message(2, "2019-03-13T13:44:55Z", "lillym", "lmorda", "Hi Lilly")))
			.block()
	}

}
