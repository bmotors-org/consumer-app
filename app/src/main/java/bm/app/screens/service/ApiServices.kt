package bm.app.screens.service

import bm.app.data.constants.ApiEndPoints
import bm.app.data.serde.OtpVerification
import bm.app.data.serde.PhoneVerification
import bm.app.ktor.ApiInterface
import bm.app.ktor.KtorHttpClient
import bm.app.ktor.utils.CustomHttpResponse
import bm.app.ktor.utils.KtorResponseException
import bm.app.ktor.utils.MissingPageException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.net.ConnectException

class ApiServices : ApiInterface {
    override suspend fun verifyPhone(phoneNumber: String): HttpResponse {
        return try {
            KtorHttpClient.post {
                url(urlString = ApiEndPoints.VERIFY_PHONE)
                setBody(PhoneVerification(phoneNumber))
            }
        } catch (cause: KtorResponseException) {
            println(cause.message)
            cause.response
        } catch (cause: MissingPageException) {
            println(cause.message)
            cause.response
        } catch (cause: ConnectException) {
            println(cause.message)
            CustomHttpResponse(500, "Server Error")
        }
    }

    override suspend fun verifyOtp(phoneNumber: String, otpCode: String): HttpResponse {
        return try {
            KtorHttpClient.post {
                url(urlString = ApiEndPoints.VERIFY_OTP)
                setBody(OtpVerification(phoneNumber, otpCode))
            }
        } catch (cause: KtorResponseException) {
            println(cause.message)
            cause.response
        } catch (cause: MissingPageException) {
            println(cause.message)
            cause.response
        } catch (cause: ConnectException) {
            println(cause.message)
            CustomHttpResponse(500, "Server Error")
        }
    }
}
