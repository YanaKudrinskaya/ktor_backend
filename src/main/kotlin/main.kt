package ru.playzone

import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.engine.embeddedServer
import org.jetbrains.exposed.sql.SchemaUtils
import ru.playzone.database.games.Games
import ru.playzone.database.tokens.Tokens
import ru.playzone.database.users.Users

fun main(args: Array<String>) {
    val dotenv = try {
        dotenv {
            filename = ".env.local"
            ignoreIfMalformed = true
            ignoreIfMissing = true
        }
    } catch (e: Exception) {
        null
    }

    val dbUrl = dotenv?.get("DATABASE_CONNECTION_STRING") ?: System.getenv("DATABASE_CONNECTION_STRING")
    val dbUser = dotenv?.get("POSTGRES_USER") ?: System.getenv("POSTGRES_USER")
    val dbPassword = dotenv?.get("POSTGRES_PASSWORD") ?: System.getenv("POSTGRES_PASSWORD")
    val serverPort = (dotenv?.get("SERVER_PORT") ?: System.getenv("PORT") ?: System.getenv("SERVER_PORT"))?.toInt() ?: 8080

    println("Starting server on port $serverPort")
    println("Database connection: $dbUrl")

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )
    SchemaUtils.create(Users, Tokens, Games)

    embeddedServer(CIO, port = serverPort, host = "0.0.0.0") {
        rootModule()
    }.start(wait = true)
}