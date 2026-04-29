package ru.playzone.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens: Table() {
    private val id = varchar("id", 50)
    private val login = varchar("login", 25)
    private val token = varchar("token", 50)

    fun insert(tokenDTO: TokenDto) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.rowId
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchTokens(): List<TokenDto> {
        return try {
            transaction {
                Tokens.selectAll()
                    .toList()
                    .map {
                        TokenDto(
                            rowId = it[Tokens.id],
                            token = it[token],
                            login = it[login]
                            )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}