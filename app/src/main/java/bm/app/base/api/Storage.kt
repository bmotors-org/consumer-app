package bm.app.base.api

import android.content.Context
import bm.app.data.preferencesDS.DataStoreKeys
import bm.app.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Storage(
    context: Context
) {
    private val dataStore = context.dataStore

    suspend fun readToken(): String {
        return dataStore.data.map {
            it[DataStoreKeys.TOKEN] ?: ""
        }.first()
    }
}
