package com.sample

data class Message(
		val created_timestamp: String,
		val recipient_login: String,
		val sender_login: String,
		val message: String
)