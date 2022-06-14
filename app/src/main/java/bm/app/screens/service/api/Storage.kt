package bm.app.screens.service.api

import android.content.Context
import androidx.datastore.preferences.core.edit
import bm.app.data.preferencesDS.DataStoreKeys
import bm.app.dataStore

class Storage(
    context: Context
) {
    private val dataStore = context.dataStore

    suspend fun storePhoneNumberAndToken(phoneNumber: String, token: String) {
        dataStore.edit {
            it[DataStoreKeys.TOKEN] = token
            it[DataStoreKeys.PHONE_NUMBER] = phoneNumber
        }
    }
}
