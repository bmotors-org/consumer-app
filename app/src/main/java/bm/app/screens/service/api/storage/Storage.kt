package bm.app.screens.service.api.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import bm.app.data.preferencesDS.DataStoreKeys
import bm.app.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Storage(
    context: Context
) {
    private val dataStore = context.dataStore

    suspend fun storeCreds(phoneNumber: String, token: String) {
        dataStore.edit {
            it[DataStoreKeys.TOKEN] = token
            it[DataStoreKeys.PHONE_NUMBER] = phoneNumber
        }
    }

    suspend fun getPhoneNumber(): String {
        return dataStore.data.map {
            it[DataStoreKeys.PHONE_NUMBER] ?: ""
        }.first()
    }
}
