package bm.app.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import bm.app.screens.home.Home
import bm.app.screens.profile.Profile
import bm.app.screens.service.Service

@Composable
fun NavLogic(
    navHostController: NavHostController,
    paddingVals: PaddingValues,
    navLogicViewModel: NavLogicViewModel = viewModel(),
) {
    val (sessionID, setSessionID) = rememberSaveable {
        mutableStateOf("")
    }

    val (phoneNumber, setPhoneNumber) = rememberSaveable {
        mutableStateOf("")
    }

    @Suppress("NAME_SHADOWING")
    LaunchedEffect(true) {
        val userData = navLogicViewModel.getUserData()
        setSessionID(userData.sessionID)
        setPhoneNumber(userData.phoneNumber)
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
                categoryName = it.arguments?.getString("categoryName") ?: ""
            )
        }
        composable(route = "profile") {
            Profile(
                sessionID = sessionID,
                phoneNumber = phoneNumber
            )
        }
    }
}
