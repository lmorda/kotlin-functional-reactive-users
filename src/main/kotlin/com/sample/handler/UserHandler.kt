package com.sample.handler

import com.sample.SampleProperties
import com.sample.User
import com.sample.repository.UserRepository
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

@Suppress("UNUSED_PARAMETER")
class UserHandler(private val repository: UserRepository, private val properties: SampleProperties) {

	fun listUsersApi(request: ServerRequest) = ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll())

	fun getUserApi(request: ServerRequest) = ok().contentType(MediaType.APPLICATION_JSON)
							.body(repository.findOne(request.pathVariable("login")))

	fun createUserApi(req: ServerRequest) = req.bodyToMono(User::class.java)
							.flatMap { user -> repository.save(user) }
							.flatMap { id -> created(URI.create("/api/users/" + id)).build() }

	fun createUserSse(req: ServerRequest) = ok().sse().body(repository.findLastOne())

	fun listUsersView(request: ServerRequest) = ok().render("users", mapOf("users" to repository.findAll()))

	fun conf(request: ServerRequest) = ok().syncBody(properties.message)

}
