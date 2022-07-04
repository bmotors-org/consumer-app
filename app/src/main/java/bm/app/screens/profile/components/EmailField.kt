package bm.app.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bm.app.screens.profile.api.network.data.MergeEmailRes
import kotlinx.coroutines.launch

@Composable
fun EmailField(
    sessionID: String,
    email: String,
    setEmail: (String) -> Unit,
    mergeEmail: suspend (String, String) -> MergeEmailRes,
    storeEmail: suspend (String) -> Unit,
) {
    println("SessionID in EmailField: $sessionID")
    val coroutineScope = rememberCoroutineScope()

    val (emailEditable, setEmailEditable) = rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp
            )
    ) {
        when (emailEditable) {
            true -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Email",
                                textAlign = TextAlign.Center
                            )
                        },
                        value = email,
                        onValueChange = { setEmail(it) },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            // TODO: reset
                            setEmailEditable(false)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null
                        )
                    }

                    // Click to save name
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                println("sessionID in 85: $sessionID")
                                val response = mergeEmail(email, sessionID)
                                if (response.success) {
                                    storeEmail(email)
                                } else {
                                    setEmail("")
                                }
                                setEmailEditable(false)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null
                        )
                    }
                }
            }

            false -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Email",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = email,
                            style = MaterialTheme.typography.titleLarge
                        )

                        IconButton(
                            onClick = { setEmailEditable(true) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}