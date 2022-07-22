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
    loadLocationFlow: StateFlow<String>,
    unLoadLocationFlow: StateFlow<String>,
    predictions: List<AutocompletePrediction?>?,
    clearPredictions: () -> Unit,
    updateLoadLocation: (String) -> Unit,
    updateUnloadLocation: (String) -> Unit
) {
    val loadLocation by loadLocationFlow.collectAsState()
    val unloadLocation by unLoadLocationFlow.collectAsState()

    var dialogPhase by remember {
        mutableStateOf<LocationPhase?>(null)
    }

    val focusManager = LocalFocusManager.current

    when (dialogPhase) {
        LocationPhase.LoadLocation -> {
            LoadLocationDialog(
                loadLocation, updateLoadLocation,
                predictions, focusManager,
            )
        }
        LocationPhase.UnloadLocation -> {
            UnloadLocationDialog(
                unloadLocation, updateUnloadLocation,
                predictions, focusManager,
            )
        }
        null -> {}
    }

    if (dialogPhase == LocationPhase.LoadLocation) {
        LoadLocationDialog(
            loadLocation, updateLoadLocation,
            predictions, focusManager,
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = loadLocation,
            onValueChange = { updateLoadLocation(it) },
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
            onValueChange = { updateUnloadLocation(it) },
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