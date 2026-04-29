package ru.playzone.database.games

import ru.playzone.features.games.models.CreateGameRequest
import ru.playzone.features.games.models.GameResponse
import java.util.UUID

data class GameDto(
    val gameId: String,
    val name: String,
    val backdrop: String?,
    val logo: String,
    val description: String,
    val downloadCount: Int,
    val version: String,
    val weight: String,
)

fun CreateGameRequest.mapToGameDTO(): GameDto =
    GameDto(
        gameId = UUID.randomUUID().toString(),
        name = title,
        description = description,
        version = version,
        weight = size,
        backdrop = "",
        logo = "",
        downloadCount = 0
    )

fun GameDto.mapToGameResponse(): GameResponse =
    GameResponse(
        gameId = gameId,
        title = name,
        description = description,
        version = version,
        size = weight
    )