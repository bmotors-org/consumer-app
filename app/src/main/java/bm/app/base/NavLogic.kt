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
        states.sessionID = userData.sessionID
        states.phoneNumber = userData.phoneNumber
        states.name = userData.name
        states.email = userData.email
        states.verified = userData.phoneNumber.isNotEmpty()
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
        ) { navBackStackEntry ->
            Service(
                categoryName = navBackStackEntry.arguments?.getString("categoryName") ?: "",
                setSessionID = { states.sessionID = it },
                verified = states.verified,
                setVerified = { states.verified = it },
                setName = { states.name = it },
                phoneNumber = states.phoneNumber,
                setPhoneNumber = { states.phoneNumber = it },
                setEmail = { states.email = it }
            )
        }
        composable(route = "profile") {
            Profile(
                sessionID = states.sessionID,
                name = states.name,
                setName = { states.name = it },
                phoneNumber = states.phoneNumber,
                email = states.email,
                setEmail = { states.email = it },
            )
        }
    }
}
