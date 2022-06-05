package bm.app.navigation

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // ktlint-disable no-wildcard-imports
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import bm.app.screens.Home
import bm.app.screens.Service

data class NavItem(val icon: ImageVector, val label: String)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val (selectedSt, setSelectedSt) = remember { mutableStateOf(0) }

    val navItems = listOf(
        NavItem(Icons.Filled.Home, "Home"),
        NavItem(Icons.Filled.Email, "Mail"),
        NavItem(Icons.Filled.Person, "Profile")
    )

    val navController = rememberNavController()

    Scaffold(bottomBar = {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            navItems.forEachIndexed(
                action = { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = null,
                                tint = when (index) {
                                    selectedSt -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.onPrimaryContainer
                                }
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        },
                        selected = selectedSt == index,
                        onClick = { setSelectedSt(index) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            )
        }
    }) {
        NavHost(navController = navController, startDestination = "home") {
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
                    navController = navController,
                    categoryName = it.arguments?.getString("categoryName") ?: ""
                )
            }
        }
    }
}
