package bm.app.base

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import bm.app.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navHostController = navHostController)
        }
    ) {
        NavLogic(navHostController = navHostController, paddingVals = it)
    }
}
