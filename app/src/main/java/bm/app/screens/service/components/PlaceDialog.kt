package bm.app.screens.service.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.libraries.places.api.model.AutocompletePrediction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlaceDialog(
    query: String,
    updateText: (String) -> Unit,
    predictions: List<AutocompletePrediction?>?,
    focusManager: FocusManager,
) {
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
                    value = query,
                    onValueChange = { updateText(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Column {
                    predictions?.forEach {
                        val primaryText = it?.getPrimaryText(null).toString()
                        val secondaryText = it?.getSecondaryText(null).toString()

                        Text(
                            text = primaryText,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .clickable {
                                    updateText(primaryText)
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