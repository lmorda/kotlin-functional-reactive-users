package com.sample.handler

import com.sample.Message
import com.sample.SampleProperties
import com.sample.User
import com.sample.repository.MessageRepository
import com.sample.repository.UserRepository
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

@Suppress("UNUSED_PARAMETER")
class MessageHandler(private val repository: MessageRepository, private val properties: SampleProperties) {

	fun listMessagesApi(request: ServerRequest) = ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll())

	fun getMessageApi(request: ServerRequest) = ok().contentType(MediaType.APPLICATION_JSON)
							.body(repository.findOne(request.pathVariable("id")))

	fun createMessageApi(req: ServerRequest) = req.bodyToMono(Message::class.java)
							.flatMap { message -> repository.save(message) }
							.flatMap { id -> created(URI.create("/api/messages/" + id)).build() }

	fun createMessageSse(req: ServerRequest) = ok().contentType(TEXT_EVENT_STREAM)
							.body(repository.findLastOne())

	fun listMessagesView(request: ServerRequest) = ok().render("messages", mapOf("messages" to repository.findAll()))

}
