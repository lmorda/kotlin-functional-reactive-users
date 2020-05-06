# kotlin-functional-reactive-users
A sample Spring Boot project to serve up users and chat messages using the Kotlin Functional (KoFu) framework.

* git clone https://github.com/lmorda/kotlin-functional-reactive-users.git
* cd kotlin-functional-reactive-users
* ./gradlew bootRun

Server will boot and run on localhost port 8080.

Based on the KoFu project
https://github.com/spring-projects-experimental/spring-fu/tree/master/kofu

This project is based on the Spring Boot Reactive R2DBC Kofu project written by Sébastien Deleuze 
https://github.com/spring-projects-experimental/spring-fu/tree/master/samples/kofu-reactive-r2dbc

## API
* GET http://127.0.0.1:8080/api/users
* GET http://127.0.0.1:8080/api/users/{login}
* POST http://127.0.0.1:8080/api/users
* GET http://127.0.0.1:8080/api/messages
* GET http://127.0.0.1:8080/api/messages/{id}
* POST http://127.0.0.1:8080/api/messages

## SSE (Flux)
* http://127.0.0.1:8080/sse/users
* http://127.0.0.1:8080/sse/messages

## Mustache
* http://127.0.0.1:8080/users
* http://127.0.0.1:8080/messages

## Configuration
* http://127.0.0.1:8080/conf

# Acknowledgements

This project is based on the Spring Boot Reactive R2DBC Kofu project written by Sébastien Deleuze
https://github.com/spring-projects-experimental/spring-fu/tree/master/samples/kofu-reactive-r2dbc

Spring Fu is an incubator for Jafu (Java DSL) and Kofu (Kotlin DSL) designed to configure Spring Boot explicitly with code in a declarative way with great discoverability thanks to auto-complete. It provides fast startup (40% faster than regular auto-configuration on a minimal Spring MVC app), low memory consumption and is a good fit with GraalVM native thanks to its (almost) reflection-less approach.  
https://github.com/spring-projects-experimental/spring-fu/tree/master/samples


