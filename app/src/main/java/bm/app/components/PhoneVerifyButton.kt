package bm.app.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bm.app.data.constants.ApiEndPoints
import bm.app.data.serde.PhoneVerification
import bm.app.ktor.ktorHttpClient
import io.ktor.client.request.* // ktlint-disable no-wildcard-imports
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
                val response =
                    ktorHttpClient.post {
                        url(urlString = ApiEndPoints.VERIFY_PHONE)
                        setBody(PhoneVerification(phoneSt))
                    }
                println(response.status)
            }
            setOtpDisplaySt(true)
        },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "Verify",
        )
    }
}
