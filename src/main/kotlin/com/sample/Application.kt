package com.sample

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
	configurationProperties<SampleProperties>(prefix = "sample")
	enable(dataConfig)
	enable(webConfig)
}

fun main() {
	app.run()
}