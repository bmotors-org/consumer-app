package bm.app.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bm.app.screens.service.api.PhoneVerificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhoneVerifyButton(
    phoneNumber: String,
    setOtpInputDialogVisibility: (Boolean) -> Unit,
    beginPhoneVerification: suspend (String) -> PhoneVerificationResponse
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch(Dispatchers.Default) {
                val response = beginPhoneVerification(phoneNumber)
                when (response.success) {
                    true -> {
                        // TODO: Implement later
                    }
                    false -> {
                        // TODO: Implement later
                    }
                }
            }
            setOtpInputDialogVisibility(true)
        },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "Verify"
        )
    }
}
