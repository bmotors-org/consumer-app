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

    LaunchedEffect(true) {
        val userData = navLogicViewModel.getUserData()
        println(userData)
        states.sessionID.value = userData.sessionID
        states.phoneNumber.value = userData.phoneNumber
        states.name.value = userData.name
        states.email.value = userData.email
        states.verified.value = userData.phoneNumber.isNotEmpty()
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
                sessionID = states.sessionID,
                verified = states.verified,
                name = states.name,
                phoneNumber = states.phoneNumber,
                email = states.email
            )
        }
        composable(
            route = "profile"
        ) {
            Profile(
                sessionID = states.sessionID,
                verified = states.verified,
                name = states.name,
                phoneNumber = states.phoneNumber,
                email = states.email,
            )
        }
    }
}
