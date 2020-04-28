package com.sample

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.http.MediaType.APPLICATION_JSON

class IntegrationTests {

	private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

	private lateinit var context: ConfigurableApplicationContext

	@BeforeAll
	fun beforeAll() {
		context = app.run(profiles = "test")
	}

	@Test
	fun `Request HTML endpoint`() {
		client.get().uri("/users").exchange()
			.expectStatus().is2xxSuccessful
			.expectHeader().contentType("text/html;charset=UTF-8")

	}

	@Test
	fun `Request HTTP API endpoint for listing all users`() {
		client.get().uri("/api/users").exchange()
				.expectStatus().is2xxSuccessful
				.expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
	}

	@Test
	fun `Request HTTP API endpoint for getting one specified user`() {
		client.get().uri("/api/users/lmorda").exchange()
				.expectStatus().is2xxSuccessful
				.expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
	}

	@Test
	fun `Request HTTP API endpoint for listing all messages`() {
		client.get().uri("/api/messages").exchange()
				.expectStatus().is2xxSuccessful
				.expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
	}

	@Test
	fun `Request HTTP API endpoint for getting one specified message`() {
		client.get().uri("/api/messages/0").exchange()
				.expectStatus().is2xxSuccessful
				.expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
	}

	@Test
	fun `Request HTTP API endpoint for creating one message`() {
		client.post().uri("/api/messages/").contentType(APPLICATION_JSON)
				.syncBody(Message(0, "2019-01-11T11:22:33Z", "lmorda", "kmorda", "Hello Lou"))
				.exchange()
				.expectStatus().is2xxSuccessful
	}

	@Test
	fun `Request conf endpoint`() {
		client.get().uri("/conf").exchange()
			.expectStatus().is2xxSuccessful
			.expectHeader().contentType("text/plain;charset=UTF-8")
	}

	@AfterAll
	fun afterAll() {
		context.close()
	}
}
