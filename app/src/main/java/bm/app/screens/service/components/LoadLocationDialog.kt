package bm.app.screens.service.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.google.android.libraries.places.api.model.AutocompletePrediction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadLocationDialog(
    loadLocation: String,
    updateLoadLocation: (String) -> Unit,
    predictions: List<AutocompletePrediction?>?,
    focusManager: FocusManager,
) {
    var expanded by remember { mutableStateOf(!predictions.isNullOrEmpty()) }

    LaunchedEffect(key1 = predictions) {
        expanded = !predictions.isNullOrEmpty()
    }

    Dialog(
        onDismissRequest = {
            focusManager.clearFocus()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                OutlinedTextField(
                    value = loadLocation,
                    onValueChange = { updateLoadLocation(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { focusManager.clearFocus() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Done"
                    )
                }
            }

            if (expanded) {
                Box(
                    modifier = Modifier
                        .offset(x = 0.dp, y = 64.dp)
                        .zIndex(2f)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column {
                        predictions?.forEach {
                            val primaryText = it?.getPrimaryText(null).toString()
                            val secondaryText = it?.getSecondaryText(null).toString()

                            Text(
                                text = primaryText,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier
                                    .clickable {
                                        updateLoadLocation(primaryText)
                                    }
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 14.dp, vertical = 8.dp
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}