package bm.app.data.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

val navItems = listOf(
    NavItem(Icons.Filled.Home, "Home", "home"),
    NavItem(Icons.Filled.Email, "Mail", "mail"),
    NavItem(Icons.Filled.Person, "Profile", "profile")
)
