package bm.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import bm.app.screens.Home
import bm.app.screens.Service

@Composable
fun NavLogic(navController: NavHostController, startDestination: String) {
    NavHost(navController, startDestination) {
        composable(route = "home") {
            Home(navController = navController)
        }
        composable(
            route = "services?categoryName={categoryName}",
            arguments = listOf(
                navArgument("categoryName") {
                    defaultValue = ""
                }
            )
        ) {
            Service(
                categoryName = it.arguments?.getString("categoryName") ?: ""
            )
        }
    }
}
