package bm.app.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.screens.profile.components.Header
import bm.app.screens.profile.components.NameField
import bm.app.screens.profile.components.Summary

@Composable
fun Profile(
    sessionID: String,
    name: String,
    setName: (String) -> Unit,
    phoneNumber: String,
    profileViewModel: ProfileViewModel = viewModel()
) {
    Column {
        Header()
        Summary(
            phoneNumber = phoneNumber
        )
        NameField(
            sessionID = sessionID,
            name = name,
            setName = setName,
            mergeName = { name, sessionID ->
                profileViewModel.mergeName(name, sessionID)
            }
        ) { name ->
            profileViewModel.storeName(name)
        }
    }
}
