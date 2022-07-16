package bm.app.screens.service

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import bm.app.BuildConfig
import bm.app.data.preferencesDS.DataStoreKeys
import bm.app.dataStore
import bm.app.ktor.KtorHttpClient
import bm.app.screens.service.api.data.*
import bm.app.screens.service.api.resources.Auth
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ServiceViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    var predictions by mutableStateOf<List<AutocompletePrediction?>?>(null)

    private val dataStore = application.dataStore

    private var placesClient: PlacesClient

    init {
        viewModelScope.launch {
            _query.debounce(1500).collect {
                fetchPlaces(it)
            }
        }

        Places.initialize(
            application,
            BuildConfig.MAPS_API_KEY
        )

        placesClient = Places.createClient(application)
    }

    private val request = FindAutocompletePredictionsRequest
        .builder()
        .setOrigin(LatLng(-33.8749937, 151.2041382))
        .setCountry("BD")
        .setTypeFilter(TypeFilter.ADDRESS)

    suspend fun storeCreds(
        phoneNumber: String, sessionID: String,
        name: String, email: String
    ) {
        dataStore.edit {
            it[DataStoreKeys.SESSION_ID] = sessionID
            it[DataStoreKeys.PHONE_NUMBER] = phoneNumber
            it[DataStoreKeys.NAME] = name
            it[DataStoreKeys.EMAIL] = email
        }
    }

    suspend fun verifyPhone(
        phoneNumber: String
    ): PhoneVerifyRes {
        return try {
            KtorHttpClient.post(resource = Auth.VerifyPhoneNumber()) {
                setBody(PhoneVerifyCreds(phoneNumber))
            }
            PhoneVerifyRes(
                success = true,
                message = "Otp sent to your phone"
            )
        } catch (cause: Exception) {
            println(cause.message)
            PhoneVerifyRes(
                success = false,
                message = cause.message ?: "An Error Occured"
            )
        }
    }

    suspend fun verifyOtp(
        phoneNumber: String,
        otpCode: String
    ): OtpVerificationRes {
        return try {
            val response = KtorHttpClient.post(
                resource = Auth.VerifyOtp()
            ) {
                setBody(OtpVerification(phoneNumber, otpCode))
            }
            val body = response.body<Creds>()
            OtpVerificationRes(
                success = true,
                message = "Otp verified successfully",
                sessionID = body.sessionID,
                name = body.name,
                email = body.email
            )
        } catch (cause: Exception) {
            println(cause.message)
            OtpVerificationRes(
                success = false,
                message = cause.message ?: "An Error Occured",
                sessionID = null,
                name = null,
                email = null
            )
        }
    }

    fun updateText(text: String) {
        _query.value = text
    }

    private fun fetchPlaces(text: String) {
        println("debounced")
        val compReq = request.setQuery(text).build()
        placesClient.findAutocompletePredictions(compReq)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                predictions = response.autocompletePredictions
            }
            .addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    Log.e(TAG, "Place not found: " + exception.statusCode)
                    exception.localizedMessage?.let { Log.e(TAG, it) }
                }
            }
    }
}
