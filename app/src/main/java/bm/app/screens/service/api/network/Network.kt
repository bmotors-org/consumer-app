package bm.app.screens.service.api.network

import bm.app.data.constants.ApiEndPoints
import bm.app.screens.service.api.network.data.Creds
import bm.app.screens.service.api.network.data.OtpVerification
import bm.app.screens.service.api.network.data.OtpVerificationRes
import bm.app.data.serde.PhoneVerification
import bm.app.data.serde.PhoneVerificationResponse
import bm.app.ktor.KtorHttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*

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
    ): OtpVerificationRes {
        return try {
            val response = KtorHttpClient.post {
                url(urlString = ApiEndPoints.VERIFY_OTP)
                setBody(OtpVerification(phoneNumber, otpCode))
            }
            val body = response.body<Creds>()
            OtpVerificationRes(
                success = true,
                message = "Otp verified successfully",
                sessionID = body.sessionID,
                name = body.name
            )
        } catch (cause: Exception) {
            println(cause.message)
            OtpVerificationRes(
                success = false,
                message = cause.message ?: "An Error Occured",
                sessionID = null,
                name = null
            )
        }
    }
}
