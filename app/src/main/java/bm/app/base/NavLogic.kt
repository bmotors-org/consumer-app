package bm.app.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import bm.app.base.api.UiState
import bm.app.screens.home.Home
import bm.app.screens.profile.Profile
import bm.app.screens.service.Service

@Composable
fun NavLogic(
    navHostController: NavHostController,
    paddingVals: PaddingValues,
    navLogicViewModel: NavLogicViewModel = viewModel(),
) {
    val states = UiState().rememberNavLogicState()

    val (verified, sessionID, phoneNumber, name, email) = states

    LaunchedEffect(true) {
        val userData = navLogicViewModel.getUserData()
        println(userData)
        sessionID.value = userData.sessionID
        phoneNumber.value = userData.phoneNumber
        name.value = userData.name
        email.value = userData.email
        verified.value = userData.phoneNumber.isNotEmpty()
    }

    NavHost(
        navHostController,
        startDestination = "home",
        modifier = Modifier.padding(
            bottom = paddingVals.calculateBottomPadding()
        )
    ) {
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
                sessionID, verified, name, phoneNumber, email
            )
        }
        composable(
            route = "profile"
        ) {
            Profile(
                sessionID, verified, name, phoneNumber, email
            )
        }
    }
}
