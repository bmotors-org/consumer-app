package bm.app.ktor.utils

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.date.*
import io.ktor.utils.io.*
import kotlin.coroutines.CoroutineContext

class CustomHttpResponse(
    private val statusCode: Int,
    private val description: String
) :
    HttpResponse() {
    @InternalAPI
    override val content: ByteReadChannel
        get() = TODO("Not yet implemented")

    override val call: HttpClientCall
        get() = TODO("Not yet implemented")

    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")

    override val headers: Headers
        get() = TODO("Not yet implemented")

    override val requestTime: GMTDate
        get() = TODO("Not yet implemented")

    override val responseTime: GMTDate
        get() = TODO("Not yet implemented")

    override val status: HttpStatusCode
        get() = HttpStatusCode(statusCode, description)

    override val version: HttpProtocolVersion
        get() = TODO("Not yet implemented")
}
