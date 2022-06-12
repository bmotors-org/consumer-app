package bm.app.screens.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.components.OtpInputDialog
import bm.app.components.PhoneVerifyButton
import bm.app.components.VerifyConfirmChip
import bm.app.dataStore
import kotlinx.coroutines.flow.map

@Composable
fun Service(
    categoryName: String,
    serviceViewModel: ServiceViewModel = viewModel()
) {
    val dataStore = LocalContext.current.dataStore

    val (verified, setVerified) = rememberSaveable { mutableStateOf(false) }
    val (otpInputDialogVisibility, setOtpInputDialogVisibility) = remember {
        mutableStateOf(false)
    }
    val (otpCode, setOtpCode) = remember { mutableStateOf("") }
    val (phoneNumber, setPhoneNumber) = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        val phonePref = stringPreferencesKey("phoneNumber")

        val phone = dataStore.data.map { preferences ->
            preferences[phonePref] ?: ""
        }
        try {
            phone.collect {
                setPhoneNumber(it)
            }
        } catch (cause: Exception) {
            println(cause.message)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { setPhoneNumber(it) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = verified,
            textStyle = TextStyle(
                fontSize = 18.sp,
                letterSpacing = 1.sp
            ),
            label = {
                Text(
                    text = "Phone Number"
                )
            },
            leadingIcon = {
                Text(
                    text = "+ 880",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(12.dp, 0.dp, 4.dp, 0.dp)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        when (verified) {
            true -> {
                VerifyConfirmChip()
            }
            false -> {
                PhoneVerifyButton(
                    phoneNumber,
                    setOtpInputDialogVisibility,
                    beginPhoneVerification = { phoneNumber: String ->
                        serviceViewModel.beignPhoneVerification(phoneNumber)
                    }
                )
            }
        }
    }

    when (otpInputDialogVisibility) {
        true -> {
            @Suppress("NAME_SHADOWING")
            OtpInputDialog(
                phoneNumber = phoneNumber,
                otpCode = otpCode,
                setOtpCode = setOtpCode,
                setOtpInputDialogVisibility = setOtpInputDialogVisibility,
                setVerified = setVerified,
                beginOtpVerification = { phoneNumber: String, otpCode: String ->
                    serviceViewModel.beginOtpVerification(phoneNumber, otpCode)
                }
            )
        }

        false -> {
        }
    }
}
