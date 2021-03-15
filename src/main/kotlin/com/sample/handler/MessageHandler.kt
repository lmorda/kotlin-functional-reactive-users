package com.sample.handler

import com.sample.SampleProperties
import com.sample.repository.MessageRepository
import io.netty.handler.codec.http.HttpHeaderValues.TEXT_EVENT_STREAM
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Suppress("UNUSED_PARAMETER")
class MessageHandler(private val repository: MessageRepository,
					 private val properties: SampleProperties) {

    fun listMessagesApi(request: ServerRequest) =
        ok().contentType(MediaType.APPLICATION_JSON)
            .body(repository.findAll())

    fun getMessageApi(request: ServerRequest) =
        ok().contentType(MediaType.APPLICATION_JSON)
            .body(repository.findOne(request.pathVariable("id")))

//	fun createMessageApi(req: ServerRequest) = req.bodyToMono(Message::class.java)
//							.flatMap { message -> repository.insert(message) }
//							.flatMap { id -> created(URI.create("/api/messages/" + id)).build() }

    fun createMessageSse(req: ServerRequest) = ok().sse().body(repository.findLastOne())

    fun listMessagesView(request: ServerRequest) =
        ok().render("messages", mapOf("messages" to repository.findAll()))

}
