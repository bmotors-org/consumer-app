package bm.app.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bm.app.ktor.ApiMethods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PhoneVerifyButton(
    phoneSt: String,
    setOtpDisplaySt: (Boolean) -> Unit,
    scope: CoroutineScope
) {
    Button(
        onClick = {
            scope.launch {
                val response = ApiMethods.verifyPhoneNumber(phoneSt)
                println(response.status)
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
