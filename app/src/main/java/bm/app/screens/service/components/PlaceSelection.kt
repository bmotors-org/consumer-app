package bm.app.screens.service.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.AutocompletePrediction
import kotlinx.coroutines.flow.StateFlow

enum class LocationPhase {
    LoadLocation, UnloadLocation
}

@Composable
fun PlaceSelection(
    locationFlow: StateFlow<String>,
    predictions: List<AutocompletePrediction?>?,
    clearPredictions: () -> Unit,
    updateLocation: (String) -> Unit
) {
    var loadLocation by remember { mutableStateOf("") }
    var unloadLocation by remember { mutableStateOf("") }

    var dialogPhase by remember {
        mutableStateOf<LocationPhase?>(null)
    }

    val focusManager = LocalFocusManager.current

    if (dialogPhase != null) {
        LocationDialog(
            updateLocationQuery = if (dialogPhase == LocationPhase.LoadLocation) {
                { loadLocation = it }
            } else {
                { unloadLocation = it }
            },
            locationFlow, updateLocation, predictions, focusManager
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = loadLocation,
            onValueChange = {},
            label = { Text(text = "Load Location") },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    dialogPhase = if (it.isFocused) {
                        LocationPhase.LoadLocation
                    } else {
                        clearPredictions()
                        null
                    }
                }
        )

        OutlinedTextField(
            value = unloadLocation,
            onValueChange = {},
            label = { Text(text = "Unload Location") },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    dialogPhase = if (it.isFocused) {
                        LocationPhase.UnloadLocation
                    } else {
                        clearPredictions()
                        null
                    }
                }
        )
    }
}