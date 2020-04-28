package com.sample

import com.sample.handler.MessageHandler
import com.sample.handler.UserHandler
import com.sample.repository.MessageRepository
import com.sample.repository.UserRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbcH2
import org.springframework.fu.kofu.webflux.mustache
import org.springframework.fu.kofu.webflux.webFlux

val dataConfig = configuration {
	beans {
		bean<UserRepository>()
		bean<MessageRepository>()
	}
	listener<ApplicationReadyEvent> {
		ref<UserRepository>().init()
		ref<MessageRepository>().init()
	}
	r2dbcH2()
}

val webConfig = configuration {
	beans {
		bean<UserHandler>()
		bean<MessageHandler>()
		bean(::routes)
	}
	webFlux {
		port = if (profiles.contains("test")) 8181 else 8080
		mustache()
		codecs {
			string()
			jackson()
		}
	}
}
