package bm.app.base.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import bm.app.data.utils.navItems

@Composable
fun BottomBar(navHostController: NavHostController) {
    val (selectedSt, setSelectedSt) = remember { mutableStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
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
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = selectedSt == index,
                onClick = {
                    val prevRoute = navHostController
                        .currentBackStackEntry
                        ?.destination
                        ?.route
                    if (prevRoute != item.route) {
                        setSelectedSt(index)
                        navHostController.navigate(route = item.route)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
