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
import kotlinx.coroutines.launch

@Composable
fun PhoneVerifyButton(
    phoneSt: String,
    setOtpDisplaySt: (Boolean) -> Unit,
    beginPhoneVerification: suspend (String) -> HttpResponse
) {
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                println(beginPhoneVerification(phoneSt))
            }
            setOtpDisplaySt(true)
        },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "Verify"
        )
    }
}
