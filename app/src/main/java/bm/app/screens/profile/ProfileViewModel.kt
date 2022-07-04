package bm.app.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import bm.app.screens.profile.api.network.Network
import bm.app.screens.profile.api.storage.Storage

class ProfileViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val networkApi = Network()

    private val storageApi = Storage(application)

    suspend fun mergeName(
        name: String,
        phoneNumber: String
    ) = networkApi.mergeName(name, phoneNumber)

    suspend fun storeName(
        name: String
    ) = storageApi.storeName(name)
}
