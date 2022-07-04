package bm.app.screens.service.api.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import bm.app.data.preferencesDS.DataStoreKeys
import bm.app.dataStore

class Storage(
    context: Context
) {
    private val dataStore = context.dataStore

    suspend fun storeCreds(
        phoneNumber: String,
        sessionID: String,
        name: String,
        email: String
    ) {
        dataStore.edit {
            it[DataStoreKeys.SESSION_ID] = sessionID
            it[DataStoreKeys.PHONE_NUMBER] = phoneNumber
            it[DataStoreKeys.NAME] = name
            it[DataStoreKeys.EMAIL] = email
        }
    }
}
