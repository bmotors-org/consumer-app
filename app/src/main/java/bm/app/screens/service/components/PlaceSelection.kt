package bm.app.screens.service.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.android.libraries.places.api.model.AutocompletePrediction
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlaceSelection(
    queryFlow: StateFlow<String>,
    predictions: List<AutocompletePrediction?>?,
    updateText: (String) -> Unit
) {
    val query by queryFlow.collectAsState()

    var expanded by remember { mutableStateOf(!predictions.isNullOrEmpty()) }

    LaunchedEffect(key1 = predictions) {
        expanded = !predictions.isNullOrEmpty()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { updateText(it) },
            modifier = Modifier.fillMaxWidth()
        )

        if (expanded) {
            Box(
                modifier = Modifier.offset(x = 0.dp, y = 32.dp).zIndex(2f)
            ) {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .offset(x = 0.dp, y = 32.dp)
                ) {
                    predictions?.forEach {
                        val primaryText = it?.getPrimaryText(null).toString()
                        val secondaryText = it?.getSecondaryText(null).toString()

                        Text(
                            text = primaryText,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .clickable {
                                    updateText(primaryText)
                                    expanded = false
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

        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, 72.dp)
                .zIndex(1f)
        )
    }

    predictions?.forEach {
        println(it?.getPrimaryText(null).toString())
    }
}