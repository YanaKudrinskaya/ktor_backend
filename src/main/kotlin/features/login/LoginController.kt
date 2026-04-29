package ru.playzone.features.login

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import ru.playzone.database.tokens.TokenDto
import ru.playzone.database.tokens.Tokens
import ru.playzone.database.users.Users
import java.util.UUID

class LoginController(
    private val call: ApplicationCall
) {
    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDto = Users.fetchUser(receive.login)
        println("receive -> $receive, dto -> $userDto")
        if (userDto == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDto.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDto(
                        rowId = UUID.randomUUID().toString(),
                        login = receive.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token))
            }
            call.respond(HttpStatusCode.BadRequest, "Invalid password")
        }
    }
}