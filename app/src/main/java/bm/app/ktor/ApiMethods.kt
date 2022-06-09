package bm.app.ktor

import bm.app.data.constants.ApiEndPoints
import bm.app.data.serde.OtpVerification
import bm.app.data.serde.PhoneVerification
import io.ktor.client.request.* // ktlint-disable no-wildcard-imports
import io.ktor.client.statement.* // ktlint-disable no-wildcard-imports

object ApiMethods {
    suspend fun verifyPhoneNumber(phoneNumber: String): HttpResponse {
        return KtorHttpClient.post {
            url(urlString = ApiEndPoints.VERIFY_PHONE)
            setBody(PhoneVerification(phoneNumber))
        }
    }

    suspend fun verifyOtp(phoneNumber: String, otpCode: String): HttpResponse {
        return KtorHttpClient.post {
            url(urlString = ApiEndPoints.VERIFY_OTP)
            setBody(OtpVerification(phoneNumber, otpCode))
        }
    }
}
