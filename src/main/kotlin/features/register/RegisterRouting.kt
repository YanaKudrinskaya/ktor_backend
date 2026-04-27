package ru.playzone.features.register

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import ru.playzone.features.cache.InMemoryCache
import ru.playzone.features.cache.TokenCache
import ru.playzone.utils.isValidEmail
import java.util.UUID

fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val receive = call.receive<RegisterReceiveRemote>()
            if (!receive.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            }
            if (InMemoryCache.userList.map { it.email }.contains(receive.email)) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }
            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.token.add(TokenCache(login = receive.login, token = token))

            call.respond(RegisterResponseRemote(token))
        }
    }
}

