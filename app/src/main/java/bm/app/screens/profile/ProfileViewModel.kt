package bm.app.screens.profile

import androidx.lifecycle.ViewModel
import bm.app.screens.profile.api.network.Network

class ProfileViewModel : ViewModel() {
    private val networkApi = Network()

    suspend fun mergeName(
        name: String,
        phoneNumber: String
    ) = networkApi.mergeName(name, phoneNumber)
}
