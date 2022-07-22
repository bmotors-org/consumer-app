package bm.app.screens.service.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.google.android.libraries.places.api.model.AutocompletePrediction

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UnloadLocationDialog(
    unloadLocation: String,
    updateUnloadLocation: (String) -> Unit,
    predictions: List<AutocompletePrediction?>?,
    focusManager: FocusManager
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .wrapContentHeight(align = Alignment.Top)
            ) {
                OutlinedTextField(
                    label = { Text(text = "Unload Location") },
                    value = unloadLocation,
                    onValueChange = { updateUnloadLocation(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { focusManager.clearFocus() },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text(
                        text = "Done",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            if (expanded) {
                Card(
                    shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.surfaceTint
                    ),
                    modifier = Modifier
                        .offset(x = 0.dp, y = 72.dp)
                        .zIndex(2f)
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        predictions?.forEach {
                            val primaryText = it?.getPrimaryText(null).toString()
                            val secondaryText = it?.getSecondaryText(null).toString()
                            val fullText = it?.getFullText(null).toString()

                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 8.dp, alignment = Alignment.CenterVertically
                                ),
                                modifier = Modifier.clickable {
                                    updateUnloadLocation(fullText)
                                }//.padding(horizontal = 8.dp, vertical = 16.dp)
                            ) {
                                Text(
                                    text = primaryText,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(
                                    text = secondaryText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Divider(
                                    startIndent = 16.dp,
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}