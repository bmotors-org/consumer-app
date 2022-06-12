package bm.app.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhoneVerifyButton(
    phoneNumber: String,
    setOtpInputDialogVisibility: (Boolean) -> Unit,
    beginPhoneVerification: suspend (String) -> HttpResponse
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch(Dispatchers.Default) {
                val response = beginPhoneVerification(phoneNumber)
                when (response.status == HttpStatusCode.InternalServerError) {
                    true -> {
                        TODO("Not yet implemented")
                    }
                    false -> {
                        TODO("Not yet implemented")
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
