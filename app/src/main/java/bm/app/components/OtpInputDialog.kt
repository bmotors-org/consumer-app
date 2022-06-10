package bm.app.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import bm.app.R
import bm.app.ktor.ApiMethods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OtpInputDialog(
    phoneSt: String,
    otpSt: String,
    setOtpSt: (String) -> Unit,
    setOtpDisplaySt: (Boolean) -> Unit,
    scope: CoroutineScope,
) {
    AlertDialog(
        onDismissRequest = { setOtpDisplaySt(false) },
        confirmButton = {
            Button(
                onClick = {
                    scope.launch {
                        val response = ApiMethods.verifyOtp(phoneSt, otpSt)
                        println(response.status)
                    }
                },
                contentPadding = PaddingValues(16.dp, 10.dp)
            ) {
                Text(
                    text = "Verify",
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    setOtpDisplaySt(false)
                    setOtpSt("")
                },
                contentPadding = PaddingValues(16.dp, 10.dp)
            ) {
                Text(
                    text = "Cancel",
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
            )
        },
        text = {
            OutlinedTextField(
                value = otpSt,
                onValueChange = { setOtpSt(it) },
                label = {
                    Text(
                        text = "OTP"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
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
