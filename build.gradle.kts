
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "ru.playzone"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.cio.EngineMain"
}

kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(ktorLibs.server.cio)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.routingOpenapi)
    implementation(ktorLibs.server.swagger)
    implementation(libs.logback.classic)

    //ktor
    implementation(libs.ktor.serialization)

    //exposed dataBase
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    //postgres
    implementation(libs.postgresql)

    // Для работы с .env файлами
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.2")

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)

}
