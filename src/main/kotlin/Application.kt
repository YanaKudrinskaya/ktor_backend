package ru.playzone

import io.ktor.server.application.Application
import ru.playzone.features.games.configureGameRouting
import ru.playzone.features.login.configureLoginRouting
import ru.playzone.features.register.configureRegisterRouting


fun Application.rootModule() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureGameRouting()
    configureHttp()
    configureSerialization()
}
