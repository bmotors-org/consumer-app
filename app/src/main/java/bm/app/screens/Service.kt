package bm.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.datastore.preferences.core.stringPreferencesKey
import bm.app.dataStore
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
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
            Dialog(
                onDismissRequest = { setVerifyPhoneCardSt(false) }
            ) {
                Card(
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(24.dp, 16.dp)
                    ) {
                        Text(
                            text = "Enter the otp sent to your phone number",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
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
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(0.dp, 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                12.dp, alignment = Alignment.End
                            )
                        ) {
                            Button(
                                onClick = {
                                    setVerifyPhoneCardSt(false)
                                    setOtpSt("")
                                },
                                contentPadding = PaddingValues(16.dp, 10.dp)
                            ) {
                                Text(
                                    text = "Cancel",
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = { /*TODO*/ },
                                contentPadding = PaddingValues(16.dp, 10.dp)
                            ) {
                                Text(
                                    text = "Verify",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        false -> {
        }
    }
}
