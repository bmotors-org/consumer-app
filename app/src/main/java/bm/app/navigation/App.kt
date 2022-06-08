package bm.app.navigation

import android.annotation.SuppressLint
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
import androidx.navigation.compose.rememberNavController
import bm.app.data.navItems

data class NavItem(val icon: ImageVector, val label: String)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val (selectedSt, setSelectedSt) = remember { mutableStateOf(0) }

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
                                style = MaterialTheme.typography.labelMedium,
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
        NavLogic(navController = navController, startDestination = "home")
    }
}
