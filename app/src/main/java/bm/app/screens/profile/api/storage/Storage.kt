package bm.app.screens.profile.api.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import bm.app.data.preferencesDS.DataStoreKeys
import bm.app.dataStore

class Storage(
    context: Context,
) {
    private val dataStore = context.dataStore

    suspend fun storeName(name: String) {
        try {
            dataStore.edit {
                it[DataStoreKeys.NAME] = name
            }
        } catch (cause: Exception) {
            println(
                "Error storing name: ${cause.localizedMessage}"
            )
        }
    }

    suspend fun storeEmail(email: String) {
        try {
            dataStore.edit {
                it[DataStoreKeys.EMAIL] = email
            }
        } catch (cause: Exception) {
            println(
                "Error storing email: ${cause.localizedMessage}"
            )
        }
    }

    suspend fun cleanCreds() {
        try {
            dataStore.edit {
                it.clear()
            }
        } catch (cause: Exception) {
            println("Error cleaning creds from disk")
        }
    }
}