package bm.app.ktor

import io.ktor.client.* // ktlint-disable no-wildcard-imports
import io.ktor.client.engine.cio.* // ktlint-disable no-wildcard-imports
import io.ktor.client.plugins.* // ktlint-disable no-wildcard-imports
import io.ktor.client.plugins.cache.* // ktlint-disable no-wildcard-imports
import io.ktor.client.plugins.contentnegotiation.* // ktlint-disable no-wildcard-imports
import io.ktor.client.plugins.logging.* // ktlint-disable no-wildcard-imports
import io.ktor.client.plugins.resources.* // ktlint-disable no-wildcard-imports
import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.serialization.kotlinx.json.* // ktlint-disable no-wildcard-imports
import io.ktor.util.* // ktlint-disable no-wildcard-imports
import kotlinx.serialization.json.Json

val ktorHttpClient = HttpClient(CIO) {
    defaultRequest {
        url {
            protocol = URLProtocol.HTTP
            host = "SERVER_BASE_URL"
            path("/api")
        }
        headers.appendIfNameAbsent(
            HttpHeaders.ContentType,
            ContentType.Application.Json.toString()
        )
    }

    install(Resources)

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
            }
        )
    }

    install(UserAgent) {
        agent = "Ktor Client"
    }

    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 3)
        exponentialDelay()
    }

    install(HttpCache)

    install(Logging) {
        level = LogLevel.INFO
    }
}
