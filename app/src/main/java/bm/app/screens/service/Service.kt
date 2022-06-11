package bm.app.screens.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.components.OtpInputDialog
import bm.app.components.PhoneVerifyButton
import bm.app.dataStore
import kotlinx.coroutines.flow.map

@Composable
fun Service(
    categoryName: String,
    serviceViewModel: ServiceViewModel = viewModel()
) {
    val dataStore = LocalContext.current.dataStore
    val (verifiedSt, setVerifiedSt) = rememberSaveable { mutableStateOf(false) }
    val (OtpDisplaySt, setOtpDisplaySt) = remember {
        mutableStateOf(
            false
        )
    }
    val (otpSt, setOtpSt) = remember { mutableStateOf("") }
    val (phoneSt, setPhoneSt) = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        val phonePref = stringPreferencesKey("phoneNumber")

        val phone = dataStore.data.map { preferences ->
            preferences[phonePref] ?: ""
        }
        try {
            phone.collect {
                setPhoneSt(it)
            }
        } catch (cause: Exception) {
            println(cause.message)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedTextField(
            value = phoneSt,
            onValueChange = { setPhoneSt(it) },
            label = {
                Text(
                    text = "Phone Number"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        when (verifiedSt) {
            true -> {}
            false -> {
                PhoneVerifyButton(
                    phoneSt,
                    setOtpDisplaySt,
                    beginPhoneVerification = { phoneNumber: String ->
                        serviceViewModel.beignPhoneVerification(phoneNumber)
                    }
                )
            }
        }
    }

    when (OtpDisplaySt) {
        true -> {
            OtpInputDialog(
                phoneSt = phoneSt,
                otpSt = otpSt,
                setOtpSt = setOtpSt,
                setOtpDisplaySt = setOtpDisplaySt,
                setVerifiedSt = setVerifiedSt,
                beginOtpVerification = { phoneNumber: String, otpCode: String ->
                    serviceViewModel.beginOtpVerification(phoneNumber, otpCode)
                }
            )
        }

        false -> {
        }
    }
}
