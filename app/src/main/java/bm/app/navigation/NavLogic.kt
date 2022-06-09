package bm.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import bm.app.screens.Home
import bm.app.screens.Service
import io.ktor.client.* // ktlint-disable no-wildcard-imports

@Composable
fun NavLogic(navHostController: NavHostController, HttpClient: HttpClient) {
    NavHost(navHostController, startDestination = "home") {
        composable(route = "home") {
            Home(navController = navHostController)
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
                categoryName = it.arguments?.getString("categoryName") ?: "",
                HttpClient = HttpClient
            )
        }
    }
}
