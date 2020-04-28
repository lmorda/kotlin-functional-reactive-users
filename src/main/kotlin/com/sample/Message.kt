package com.sample

import org.springframework.data.annotation.Id

data class Message(
		@Id var id: Int,
		val created_timestamp: String,
		val recipient_login: String,
		val sender_login: String,
		val message: String
)