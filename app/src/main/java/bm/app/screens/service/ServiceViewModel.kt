package bm.app.screens.service

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import bm.app.screens.service.api.Network
import bm.app.screens.service.api.Storage

class ServiceViewModel(application: Application) : AndroidViewModel(application) {
    private val networkApi = Network()

    private val storageApi = Storage(application)

    suspend fun phoneVerification(
        phoneNumber: String
    ) = networkApi.verifyPhone(phoneNumber)

    suspend fun otpVerification(
        phoneNumber: String,
        otpCode: String
    ) = networkApi.verifyOtp(phoneNumber, otpCode)

    suspend fun saveToStorage(
        phoneNumber: String,
        token: String
    ) = storageApi.storePhoneNumberAndToken(phoneNumber, token)

    suspend fun getPhoneNumberFromStorage() = storageApi.getPhoneNumber()
}
