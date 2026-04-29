package ru.playzone.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table() {
    private val login = varchar("login", 25)
    private val password = varchar("password", 25)
    private val username = varchar("username", 30)
    private val email = varchar("email", 25).nullable()

    fun insert(userDTO: UserDto) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email
            }
        }
    }

    fun fetchUser(login: String): UserDto? {
        return try {
            transaction {
                val userModel = Users.select { Users.login.eq(login)}.single()
                UserDto(
                    login = userModel[Users.login],
                    password = userModel[password],
                    username = userModel[username],
                    email = userModel[email]
                )
            }
        } catch (e: Exception) {
           null
        }
    }
}