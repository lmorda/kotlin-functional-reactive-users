package com.sample

import com.sample.handler.MessageHandler
import com.sample.handler.UserHandler
import org.springframework.web.reactive.function.server.router

fun routes(userHandler: UserHandler, messageHandler: MessageHandler) = router {

	// Users
	GET("/users", userHandler::listUsersView)
	GET("/api/users", userHandler::listUsersApi)
	GET("/api/users/{login}", userHandler::getUserApi)
	POST("/api/users", userHandler::createUserApi)

	// Messages
	GET("/messages", messageHandler::listMessagesView)
	GET("/api/messages", messageHandler::listMessagesApi)
	GET("/api/messages/{id}", messageHandler::getMessageApi)
	POST("/api/messages", messageHandler::createMessageApi)
	GET("/sse/messages", messageHandler::createMessageSse)

}
