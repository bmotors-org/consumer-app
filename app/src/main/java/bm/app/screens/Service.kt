package bm.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.datastore.preferences.core.stringPreferencesKey
import bm.app.R
import bm.app.dataStore
import kotlinx.coroutines.flow.map

@Composable
fun Service(categoryName: String) {
    val dataStore = LocalContext.current.dataStore
    val (verifiedSt, setVerifiedSt) = rememberSaveable { mutableStateOf(false) }
    val (verifyPhoneCardSt, setVerifyPhoneCardSt) = remember {
        mutableStateOf(
            false
        )
    }
    val (otpSt, setOtpSt) = remember { mutableStateOf("") }
    val (phoneSt, setPhoneSt) = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        val phonePref = stringPreferencesKey("phoneNumber")

        val phone = dataStore.data.map { preferences ->
            preferences[phonePref] ?: ""
        }
        try {
            phone.collect {
                setPhoneSt(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineLarge,
        )
        OutlinedTextField(
            value = phoneSt, onValueChange = { setPhoneSt(it) }, label = {
                Text(
                    text = "Phone Number"
                )
            }, modifier = Modifier.fillMaxWidth()
        )

        when (verifiedSt) {
            true -> {}
            false -> {
                Button(
                    onClick = { setVerifyPhoneCardSt(true) },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text(
                        text = "Verify",
                    )
                }
            }
        }
    }

    when (verifyPhoneCardSt) {
        true -> {
            AlertDialog(
                onDismissRequest = { setVerifyPhoneCardSt(false) },
                confirmButton = {
                    Button(
                        onClick = { /*TODO*/ },
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
                            setVerifyPhoneCardSt(false)
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
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
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
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = 8.dp,
                iconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                textContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    securePolicy = SecureFlagPolicy.SecureOn
                )
            )
        }

        false -> {
        }
    }
}
