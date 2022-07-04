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
                "Error saving name: ${cause.localizedMessage}"
            )
        }

    }
}