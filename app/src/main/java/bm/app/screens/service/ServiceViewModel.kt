package bm.app.screens.service

import androidx.lifecycle.ViewModel
import io.ktor.client.statement.*

class ServiceViewModel() : ViewModel() {
    private val apiServices = ApiServices()

    suspend fun beignPhoneVerification(phoneNumber: String): HttpResponse {
        return apiServices.verifyPhone(phoneNumber)
    }

    suspend fun beginOtpVerification(phoneNumber: String, otpCode: String): HttpResponse {
        return apiServices.verifyOtp(phoneNumber, otpCode)
    }
}
