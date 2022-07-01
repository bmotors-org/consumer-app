package bm.app.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bm.app.screens.profile.api.network.responses.MergeName
import kotlinx.coroutines.launch

@Composable
fun NameField(
    token: String,
    mergeName: suspend (String, String) -> MergeName
) {
    val coroutineScope = rememberCoroutineScope()

    val (nameEditable, setNameEditable) = rememberSaveable {
        mutableStateOf(false)
    }

    val (name, setName) = rememberSaveable {
        mutableStateOf("")
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
    ) {
        when (nameEditable) {
            true -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Name",
                                textAlign = TextAlign.Center
                            )
                        },
                        value = name,
                        onValueChange = { setName(it) },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            // TODO: reset
                            setNameEditable(false)
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
                        text = "Name",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "John Doe",
                            style = MaterialTheme.typography.titleLarge
                        )

                        IconButton(
                            onClick = { setNameEditable(true) }
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
