package bm.app.ktor

import io.ktor.client.* // ktlint-disable no-wildcard-imports
import io.ktor.client.engine.cio.* // ktlint-disable no-wildcard-imports
import io.ktor.client.plugins.* // ktlint-disable no-wildcard-imports
import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.util.* // ktlint-disable no-wildcard-imports

val client = HttpClient(CIO) {
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

    install(UserAgent) {
        agent = "Ktor Client"
    }

    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 3)
        exponentialDelay()
    }
}
