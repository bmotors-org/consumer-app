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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    setSessionID: (String) -> Unit,
    verified: Boolean,
    setVerified: (Boolean) -> Unit,
    setName: (String) -> Unit,
    phoneNumber: String,
    setPhoneNumber: (String) -> Unit,
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
            ),
            maxLines = 1
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
            setSessionID = setSessionID,
            verified = verified,
            setVerified = setVerified,
            setName = setName,
            setOtpInputDialogVisibility = setOtpInputDialogVisibility,
            verifyOtp = { phoneNumber: String, otpCode: String ->
                serviceViewModel.verifyOtp(phoneNumber, otpCode)
            }
        ) { phoneNumber: String, sessionID: String, name: String ->
            serviceViewModel.storeCreds(phoneNumber, sessionID, name)
        }
    }
}
