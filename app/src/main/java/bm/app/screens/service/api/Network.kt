package bm.app.screens.service.api

import bm.app.data.constants.ApiEndPoints
import bm.app.data.serde.JwtToken
import bm.app.data.serde.OtpVerification
import bm.app.data.serde.PhoneVerification
import bm.app.ktor.KtorHttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*

data class PhoneVerificationResponse(val success: Boolean, val message: String)
data class OtpVerificationResponse(
    val success: Boolean,
    val message: String,
    val token: String
)

class Network {
    suspend fun verifyPhone(phoneNumber: String): PhoneVerificationResponse {
        return try {
            KtorHttpClient.post {
                url(urlString = ApiEndPoints.VERIFY_PHONE)
                setBody(PhoneVerification(phoneNumber))
            }
            PhoneVerificationResponse(success = true, message = "Otp sent to your phone")
        } catch (cause: Exception) {
            println(cause.message)
            PhoneVerificationResponse(
                success = false,
                message = cause.message ?: "An Error Occured"
            )
        }
    }

    suspend fun verifyOtp(
        phoneNumber: String,
        otpCode: String
    ): OtpVerificationResponse {
        return try {
            val response = KtorHttpClient.post {
                url(urlString = ApiEndPoints.VERIFY_OTP)
                setBody(OtpVerification(phoneNumber, otpCode))
            }
            val body = response.body<JwtToken>()
            OtpVerificationResponse(
                success = true,
                message = "Otp verified successfully",
                token = body.token
            )
        } catch (cause: Exception) {
            println(cause.message)
            OtpVerificationResponse(
                success = false,
                message = cause.message ?: "An Error Occured",
                token = ""
            )
        }
    }
}
