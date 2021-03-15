package com.sample.repository

import com.sample.Message
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class MessageRepository(private val client: DatabaseClient) {

	fun count() =
		client.sql("SELECT COUNT(created_timestamp) FROM messages")
			.map { row -> (row.get(0) as Long).toInt() }
			.first()

	fun findAll(): Flux<Message> =
		client.sql("SELECT * FROM messages")
			.map { row ->
				Message(
					created_timestamp = row.get("created_timestamp", String::class.java)!!,
					recipient_login = row.get("recipient_login", String::class.java)!!,
					sender_login = row.get("sender_login", String::class.java)!!,
					message = row.get("message", String::class.java)!!,
				)
			}
			.all()

	fun findOne(created_timestamp: String?): Mono<Message> =
		created_timestamp?.let {
			client.sql("SELECT * FROM messages WHERE created_timestamp = :created_timestamp")
				.bind("created_timestamp", it)
				.map { row ->
					Message(
						created_timestamp = row.get("created_timestamp", String::class.java)!!,
						recipient_login = row.get("recipient_login", String::class.java)!!,
						sender_login = row.get("sender_login", String::class.java)!!,
						message = row.get("message", String::class.java)!!,
					)
				}
				.first()
		} ?: Mono.empty()

	fun findLastOne() =
		client.sql("SELECT * FROM messages ORDER BY created_timestamp DESC LIMIT 1")
			.map { row ->
				Message(
					created_timestamp = row.get("created_timestamp", String::class.java)!!,
					recipient_login = row.get("recipient_login", String::class.java)!!,
					sender_login = row.get("sender_login", String::class.java)!!,
					message = row.get("message", String::class.java)!!,
				)
			}.first().repeat().distinctUntilChanged()

	fun insert(message: Message) =
		client.sql("INSERT INTO messages(created_timestamp, recipient_login, sender_login, message) " +
				"values(:created_timestamp, :recipient_login, :sender_login, :message)")
			.bind("created_timestamp", message.created_timestamp)
			.bind("recipient_login", message.recipient_login)
			.bind("sender_login", message.sender_login)
			.bind("message", message.message)

}
