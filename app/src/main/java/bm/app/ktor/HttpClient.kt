package bm.app.ktor

import bm.app.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

val KtorHttpClient = HttpClient(CIO) {
    expectSuccess = true

    defaultRequest {
        url {
            protocol = URLProtocol.HTTP
            host = BuildConfig.HOST
            port = (BuildConfig.PORT).toInt()
            path("api/")
        }
        headers.appendIfNameAbsent(
            name = "Content-Type",
            value = "application/json"
        )
    }

    install(Resources)

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(UserAgent) {
        agent = "Ktor Client"
    }

    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 0)
        retryOnException(maxRetries = 0)
        exponentialDelay()
    }

    install(HttpCache)

    install(Logging) {
        level = LogLevel.INFO
    }
}
