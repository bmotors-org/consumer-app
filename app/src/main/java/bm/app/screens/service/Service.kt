package bm.app.screens.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.screens.service.components.OtpInputDialog
import bm.app.screens.service.components.PhoneVerifyButton
import bm.app.screens.service.components.PlaceSelection
import bm.app.screens.service.components.VerifyConfirmChip

@Composable
fun Service(
    categoryName: String,
    sessionID: MutableState<String>,
    verified: MutableState<Boolean>,
    name: MutableState<String>,
    phoneNumber: MutableState<String>,
    email: MutableState<String>,
    serviceViewModel: ServiceViewModel = viewModel()
) {
    val (otpInputDialogVisibility, setOtpInputDialogVisibility) = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = {
                if (it.length <= 10) {
                    phoneNumber.value = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = verified.value,
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

        when (verified.value) {
            true -> {
                VerifyConfirmChip()
            }
            false -> {
                PhoneVerifyButton(
                    phoneNumber, setOtpInputDialogVisibility
                ) { phoneNumber: String ->
                    serviceViewModel.verifyPhone(phoneNumber)
                }
            }
        }

        PlaceSelection(
            loadLocationFlow = serviceViewModel.loadLocation,
            unLoadLocationFlow = serviceViewModel.unloadLocation,
            predictions = serviceViewModel.predictions,
            clearPredictions = { serviceViewModel.clearPredictions() },
            updateLoadLocation = { text: String ->
                serviceViewModel.updateLoadLocation(text)
            }
        ) { text: String ->
            serviceViewModel.updateUnloadLocation(text)
        }

        if (otpInputDialogVisibility) {
            @Suppress("NAME_SHADOWING") OtpInputDialog(
                phoneNumber, sessionID, verified, name, email,
                setOtpInputDialogVisibility = setOtpInputDialogVisibility,
                verifyOtp = { phoneNumber: String, otpCode: String ->
                    serviceViewModel.verifyOtp(phoneNumber, otpCode)
                }) { phoneNumber: String, sessionID: String, name: String, email: String ->
                serviceViewModel.storeCreds(
                    phoneNumber, sessionID, name, email
                )
            }
        }
    }
}
