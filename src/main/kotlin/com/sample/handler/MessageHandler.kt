package com.sample.handler

import com.sample.Message
import com.sample.User
import com.sample.repository.MessageRepository
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

class MessageHandler(private val repository: MessageRepository) {

    fun listMessagesApi(request: ServerRequest) =
        ok().contentType(MediaType.APPLICATION_JSON)
            .body(repository.findAll())

    fun getMessageApi(request: ServerRequest) =
        ok().contentType(MediaType.APPLICATION_JSON)
            .body(repository.findOne(request.pathVariable("id")))

    fun createMessageApi(req: ServerRequest) =
        req.bodyToMono(Message::class.java)
            .flatMap { message -> repository.insert(message) }
            .flatMap { id -> ServerResponse.created(URI.create("/api/messages/" + id)).build() }

    fun createMessageSse(req: ServerRequest) = ok().sse().body(repository.findLastOne())

    fun listMessagesView(request: ServerRequest) =
        ok().render("messages", mapOf("messages" to repository.findAll()))

}
