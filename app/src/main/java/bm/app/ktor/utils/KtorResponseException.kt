package bm.app.ktor.utils

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.*

class KtorResponseException(response: HttpResponse, cachedResponseText: String) :
    ResponseException(response, cachedResponseText) {
    override val message: String = "Custom server error: ${response.call.request.url}. " +
        "Status: ${response.status}. Text: \"$cachedResponseText\""
}
