package bm.app.screens.service.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
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

    val interactionSource = remember { MutableInteractionSource() }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = predictions) {
        expanded = !predictions.isNullOrEmpty()
    }

    if (interactionSource.collectIsFocusedAsState().value) {
        PlaceDialog(
            query = query,
            updateText = updateText,
            predictions = predictions,
            focusManager = focusManager,
        )
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
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth()
        )

        /*if (expanded) {
            Box(
                modifier = Modifier
                    .offset(x = 0.dp, y = 64.dp)
                    .zIndex(2f)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {

            }
        }*/

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