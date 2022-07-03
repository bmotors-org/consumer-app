package bm.app.screens.service.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyConfirmChip() {
    SuggestionChip(
        onClick = {
            /*TODO*/
        },
        icon = {
            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null)
        },
        label = {
            Text("Verified")
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            iconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}
