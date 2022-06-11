package bm.app.data.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import bm.app.navigation.NavItem

val navItems = listOf(
    NavItem(Icons.Filled.Home, "Home"),
    NavItem(Icons.Filled.Email, "Mail"),
    NavItem(Icons.Filled.Person, "Profile")
)
