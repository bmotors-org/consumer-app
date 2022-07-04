package bm.app.data.preferencesDS

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val SESSION_ID = stringPreferencesKey("sessionID")
    val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
    val NAME = stringPreferencesKey("name")
    val EMAIL = stringPreferencesKey("email")
}
