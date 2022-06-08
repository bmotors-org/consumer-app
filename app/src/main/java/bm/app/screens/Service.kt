package bm.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import bm.app.components.OtpInputDialog
import bm.app.data.serde.PhoneNumber
import bm.app.dataStore
import bm.app.ktor.ktorHttpClient
import io.ktor.client.request.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun Service(categoryName: String) {
    val dataStore = LocalContext.current.dataStore
    val (verifiedSt, setVerifiedSt) = rememberSaveable { mutableStateOf(false) }
    val (OtpDisplaySt, setOtpDisplaySt) = remember {
        mutableStateOf(
            false
        )
    }
    val (otpSt, setOtpSt) = remember { mutableStateOf("") }
    val (phoneSt, setPhoneSt) = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        val phonePref = stringPreferencesKey("phoneNumber")

        val phone = dataStore.data.map { preferences ->
            preferences[phonePref] ?: ""
        }
        try {
            phone.collect {
                setPhoneSt(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineLarge,
        )
        OutlinedTextField(
            value = phoneSt, onValueChange = { setPhoneSt(it) }, label = {
                Text(
                    text = "Phone Number"
                )
            }, modifier = Modifier.fillMaxWidth()
        )

        when (verifiedSt) {
            true -> {}
            false -> {
                Button(
                    onClick = {
                        scope.launch {
                            val response =
                                ktorHttpClient.post {
                                    url(urlString = "verify-phone-number")
                                    setBody(PhoneNumber(phoneSt))
                                }
                            println(response.status)
                        }
                        setOtpDisplaySt(true)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text(
                        text = "Verify",
                    )
                }
            }
        }
    }

    when (OtpDisplaySt) {
        true -> {
            OtpInputDialog(
                otpSt = otpSt,
                setOtpSt = setOtpSt,
                setOtpDisplaySt = setOtpDisplaySt
            )
        }

        false -> {
        }
    }
}
