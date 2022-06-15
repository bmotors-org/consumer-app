package bm.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import bm.app.R
import bm.app.screens.service.api.OtpVerificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OtpInputDialog(
    phoneNumber: String,
    otpCode: String,
    setOtpCode: (String) -> Unit,
    setOtpInputDialogVisibility: (Boolean) -> Unit,
    setVerified: (Boolean) -> Unit,
    otpVerification: suspend (String, String) -> OtpVerificationResponse,
    saveToStorage: suspend (String, String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val (waiting, setWaiting) = rememberSaveable {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = { setOtpInputDialogVisibility(false) },
        confirmButton = {
            Button(
                enabled = !waiting,
                onClick = {
                    setWaiting(true)
                    coroutineScope.launch(Dispatchers.Default) {
                        val response = otpVerification(phoneNumber, otpCode)
                        if (response.success) {
                            saveToStorage(phoneNumber, response.token)
                            setVerified(true)
                            setOtpInputDialogVisibility(false)
                        } else {
                            setWaiting(false)
                        }
                    }
                },
                contentPadding = PaddingValues(16.dp, 10.dp)
            ) {
                Text(
                    text = "Verify"
                )
            }
        },
        dismissButton = {
            TextButton(
                enabled = !waiting,
                onClick = {
                    setOtpInputDialogVisibility(false)
                    setOtpCode("")
                },
                contentPadding = PaddingValues(16.dp, 10.dp)
            ) {
                Text(
                    text = "Cancel"
                )
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Enter the otp sent to your phone number",
                textAlign = TextAlign.Center
            )
        },
        text = {
            when (waiting) {
                true -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }

                false -> {
                    OutlinedTextField(
                        value = otpCode,
                        onValueChange = {
                            if (it.length <= 4) {
                                setOtpCode(it)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            letterSpacing = 1.sp
                        ),
                        label = {
                            Text(
                                text = "OTP"
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        iconContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOn
        )
    )
}
