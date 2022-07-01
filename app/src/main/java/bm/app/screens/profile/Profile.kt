package bm.app.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.screens.profile.components.Header
import bm.app.screens.profile.components.NameField
import bm.app.screens.profile.components.Summary

@Composable
fun Profile(
    profileViewModel: ProfileViewModel = viewModel(),
    token: String
) {
    Column {
        Header()
        Summary()
        NameField(
            token = token
        ) { name, phoneNumber ->
            profileViewModel.mergeName(name, phoneNumber)
        }
    }
}
