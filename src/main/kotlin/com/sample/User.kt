package com.sample

import org.springframework.data.annotation.Id

data class User(
		@Id var id: Int,
		val login: String,
		val firstname: String,
		val lastname: String,
		val phonenumber: String,
		val birthdate: String,
		val description: String,
		val avatar: String
)