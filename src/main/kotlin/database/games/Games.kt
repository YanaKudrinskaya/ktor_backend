package ru.playzone.database.games

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Games: Table() {
    private val gameId = varchar("gameId", 100)
    private val name = varchar("name", 100)
    private val backdrop = varchar("backdrop", 50).nullable()
    private val logo = varchar("logo", 50)
    private val description = varchar("description", 500)
    private val downloadCount = integer("download_count")
    private val version = varchar("version", 15)
    private val weight = varchar("weight", 10)

    fun insert(gameDTO: GameDto) {
        transaction {
            Games.insert {
                it[gameId] = gameDTO.gameId
                it[name] = gameDTO.name
                it[backdrop] = gameDTO.backdrop
                it[logo] = gameDTO.logo
                it[description] = gameDTO.description
                it[downloadCount] = gameDTO.downloadCount
                it[version] = gameDTO.version
                it[weight] = gameDTO.weight
            }
        }
    }
    fun fetchGames(): List<GameDto> {
        return try {
            transaction {
                Games.selectAll().toList().map {
                    GameDto(
                        gameId = it[gameId],
                        name = it[name],
                        backdrop = it[backdrop],
                        logo = it[logo],
                        description = it[description],
                        downloadCount = it[downloadCount],
                        version = it[version],
                        weight = it[weight],
                    )
                }

            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}