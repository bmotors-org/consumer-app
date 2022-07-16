package bm.app.screens.service.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bm.app.screens.service.api.data.PhoneVerifyRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhoneVerifyButton(
    phoneNumber: MutableState<String>,
    setOtpInputDialogVisibility: (Boolean) -> Unit,
    verifyPhone: suspend (String) -> PhoneVerifyRes
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            // Testing code
            if (phoneNumber.value.isEmpty()) {
                setOtpInputDialogVisibility(true)
                return@Button
            }
            //
            setOtpInputDialogVisibility(true)
            coroutineScope.launch(Dispatchers.Default) {
                val response = verifyPhone(phoneNumber.value)
                if (!response.success) {
                    setOtpInputDialogVisibility(false)
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "Verify"
        )
    }
}
