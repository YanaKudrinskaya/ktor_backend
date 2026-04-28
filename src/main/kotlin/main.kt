package ru.playzone

import io.ktor.server.engine.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import io.github.cdimascio.dotenv.dotenv

fun main(args: Array<String>) {

    val dotenv = dotenv {
        filename = ".env.local"
        ignoreIfMalformed = true
        ignoreIfMissing = false
    }

    Database.connect(
        url = dotenv["DATABASE_CONNECTION_STRING"],
        driver = "org.postgresql.Driver",
        user = dotenv["POSTGRES_USER"],
        password = dotenv["POSTGRES_PASSWORD"]
    )
    embeddedServer(
        factory = io.ktor.server.cio.CIO,
        port = dotenv["SERVER_PORT"].toInt(),
        host = "0.0.0.0",
        module = Application::rootModule
    ).start(wait = true)
}
