package bm.app.base.api.storage

import android.content.Context
import bm.app.base.api.storage.data.UserData
import bm.app.data.preferencesDS.DataStoreKeys
import bm.app.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Storage(
    context: Context
) {
    private val dataStore = context.dataStore

    suspend fun getUserData(): UserData {
        return dataStore.data.map {
            val sessionID = it[DataStoreKeys.SESSION_ID] ?: ""
            val phoneNumber = it[DataStoreKeys.PHONE_NUMBER] ?: ""
            UserData(sessionID, phoneNumber)
        }.first()
    }
}
