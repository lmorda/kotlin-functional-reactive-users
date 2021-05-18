package com.sample.handler

import com.sample.User
import com.sample.repository.UserRepository
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

class UserHandler(private val repository: UserRepository) {

    fun listUsersApi(request: ServerRequest) =
        ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll())

    fun getUserApi(request: ServerRequest) =
        ok().contentType(MediaType.APPLICATION_JSON)
            .body(repository.findOne(request.pathVariable("login")))

	fun createUserApi(req: ServerRequest) =
        req.bodyToMono(User::class.java)
            .flatMap { user -> repository.insert(user) }
            .flatMap { id -> created(URI.create("/api/users/" + id)).build() }


    fun listUsersView(request: ServerRequest) =
    	ok().render("users", mapOf("users" to repository.findAll()))

}
