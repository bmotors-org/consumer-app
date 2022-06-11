package bm.app.ktor

import bm.app.ktor.utils.MissingPageException
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

val KtorHttpClient = HttpClient(CIO) {
    expectSuccess = true

    defaultRequest {
        url {
            protocol = URLProtocol.HTTP
            host = "SERVER_BASE_URL"
            port = 4000
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

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            if (exception !is ClientRequestException) return@handleResponseExceptionWithRequest
            val exceptionResponse = exception.response
            if (exceptionResponse.status == HttpStatusCode.NotFound) {
                val exceptionResponseText = exceptionResponse.bodyAsText()
                throw MissingPageException(
                    exceptionResponse,
                    exceptionResponseText
                )
            }
        }
    }

    install(UserAgent) {
        agent = "Ktor Client"
    }

    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 3)
        retryOnException(maxRetries = 3)
        exponentialDelay()
    }

    install(HttpCache)

    install(Logging) {
        level = LogLevel.INFO
    }
}
