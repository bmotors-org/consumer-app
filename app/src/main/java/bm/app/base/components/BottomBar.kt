package bm.app.base.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import bm.app.data.utils.navItems

@Composable
fun BottomBar(navHostController: NavHostController) {
    val (selectedSt, setSelectedSt) = remember { mutableStateOf(0) }

    NavigationBar(
        modifier = Modifier.height(64.dp)
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = null,
                        tint = when (index) {
                            selectedSt -> MaterialTheme.colorScheme.primaryContainer
                            else -> MaterialTheme.colorScheme.onPrimaryContainer
                        },
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = null,
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
