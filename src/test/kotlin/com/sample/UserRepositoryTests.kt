package com.sample

import com.sample.repository.UserRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.reactiveWebApplication

class UserRepositoryTests {

	val app = reactiveWebApplication {
		configurationProperties<SampleProperties>(prefix = "sample")
		enable(dataConfig)
		enable(webConfig)
	}

	private lateinit var context: ConfigurableApplicationContext

	@BeforeAll
	fun beforeAll() {
		context = app.run(profiles = "test")
	}

	@Test
	fun count() {
		val repository = context.getBean<UserRepository>()
		assertEquals(4, repository.count().block())
	}

	@AfterAll
	fun afterAll() {
		context.close()
	}
}
