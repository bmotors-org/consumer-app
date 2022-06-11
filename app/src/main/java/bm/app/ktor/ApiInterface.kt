package bm.app.ktor

import io.ktor.client.statement.*

interface ApiInterface {
    suspend fun verifyPhone(phoneNumber: String): HttpResponse

    suspend fun verifyOtp(phoneNumber: String, otpCode: String): HttpResponse
}
