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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.screens.service.components.OtpInputDialog
import bm.app.screens.service.components.PhoneVerifyButton
import bm.app.screens.service.components.VerifyConfirmChip

@Composable
fun Service(
    categoryName: String,
    serviceViewModel: ServiceViewModel = viewModel()
) {
    val (verified, setVerified) = rememberSaveable { mutableStateOf(false) }
    val (otpInputDialogVisibility, setOtpInputDialogVisibility) = remember {
        mutableStateOf(false)
    }
    val (otpCode, setOtpCode) = remember { mutableStateOf("") }
    val (phoneNumber, setPhoneNumber) = remember { mutableStateOf("") }

    @Suppress("NAME_SHADOWING")
    LaunchedEffect(true) {
        val phoneNumber = serviceViewModel.getPhoneNumberFromStorage()
        setPhoneNumber(phoneNumber)
        setVerified(phoneNumber.isNotEmpty())
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
            onValueChange = {
                if (it.length <= 10) {
                    setPhoneNumber(it)
                }
            },
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
                    setOtpInputDialogVisibility
                ) { phoneNumber: String ->
                    serviceViewModel.verifyPhone(phoneNumber)
                }
            }
        }
    }

    if (otpInputDialogVisibility) {
        @Suppress("NAME_SHADOWING")
        OtpInputDialog(
            phoneNumber = phoneNumber,
            otpCode = otpCode,
            verified = verified,
            setOtpCode = setOtpCode,
            setOtpInputDialogVisibility = setOtpInputDialogVisibility,
            setVerified = setVerified,
            verifyOtp = { phoneNumber: String, otpCode: String ->
                serviceViewModel.verifyOtp(phoneNumber, otpCode)
            }
        ) { phoneNumber: String, sessionID: String ->
            serviceViewModel.storeCreds(phoneNumber, sessionID)
        }
    }
}
