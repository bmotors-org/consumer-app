package bm.app.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import bm.app.base.api.storage.Storage

class NavLogicViewModel(application: Application) : AndroidViewModel(application) {
    private val storageApi = Storage(application)

    suspend fun getUserData() = storageApi.getUserData()
}
