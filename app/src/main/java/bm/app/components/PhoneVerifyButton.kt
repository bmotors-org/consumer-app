package bm.app.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bm.app.data.serde.PhoneVerificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhoneVerifyButton(
    phoneNumber: String,
    setOtpInputDialogVisibility: (Boolean) -> Unit,
    verifyPhone: suspend (String) -> PhoneVerificationResponse
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch(Dispatchers.Default) {
                val response = verifyPhone(phoneNumber)
                when (response.success) {
                    true -> {
                        // TODO: Implement later
                    }
                    false -> {
                        setOtpInputDialogVisibility(false)
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
