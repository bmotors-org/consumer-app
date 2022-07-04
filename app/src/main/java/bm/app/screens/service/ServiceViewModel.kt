package bm.app.screens.service

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import bm.app.screens.service.api.network.Network
import bm.app.screens.service.api.storage.Storage

class ServiceViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val networkApi = Network()

    private val storageApi = Storage(application)

    suspend fun verifyPhone(
        phoneNumber: String
    ) = networkApi.verifyPhone(phoneNumber)

    suspend fun verifyOtp(
        phoneNumber: String,
        otpCode: String
    ) = networkApi.verifyOtp(
        phoneNumber, otpCode
    )

    suspend fun storeCreds(
        phoneNumber: String,
        sessionID: String,
        name: String
    ) = storageApi.storeCreds(
        phoneNumber, sessionID, name
    )
}
