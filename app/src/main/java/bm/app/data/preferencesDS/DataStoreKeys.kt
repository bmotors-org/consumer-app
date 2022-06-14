package bm.app.data.preferencesDS

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val TOKEN = stringPreferencesKey("token")
    val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
}