package bm.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import bm.app.components.Categories
import bm.app.components.Header

@Composable
fun Home(navController: NavController) {
    Column {
        Header()
        Spacer(modifier = Modifier.size(24.dp))
        Categories(navController = navController)
    }
}
