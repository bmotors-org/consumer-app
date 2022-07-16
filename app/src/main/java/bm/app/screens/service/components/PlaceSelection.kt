package bm.app.screens.service.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    println("predictions null ${predictions.isNullOrEmpty()}")
    println("expanded is $expanded")

    LaunchedEffect(key1 = predictions) {
        expanded = !predictions.isNullOrEmpty()
        println("Here, expanded is $expanded")
    }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = { updateText(it) }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.tertiary
                )
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                predictions?.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it?.getPrimaryText(null).toString())
                        },
                        onClick = { expanded = false }
                    )
                }
            }
        }
    }

    predictions?.forEach {
        println(it?.getPrimaryText(null).toString())
    }
}