package bm.app.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.screens.profile.components.*

@Composable
fun Profile(
    sessionID: MutableState<String>,
    verified: MutableState<Boolean>,
    name: MutableState<String>,
    phoneNumber: MutableState<String>,
    email: MutableState<String>,
    profileViewModel: ProfileViewModel = viewModel()
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        Header()
        Spacer(modifier = Modifier.size(16.dp))
        Summary(phoneNumber)
        NameField(
            sessionID, name,
            mergeName = { name, sessionID ->
                profileViewModel.mergeName(name, sessionID)
            }
        ) { name ->
            profileViewModel.storeName(name)
        }

        EmailField(
            sessionID, email,
            mergeEmail = { email, sessionID ->
                profileViewModel.mergeEmail(email, sessionID)
            }
        ) { email ->
            profileViewModel.storeEmail(email)
        }

        Spacer(modifier = Modifier.size(24.dp))

        if (verified.value) {
            LogoutButton(
                cleanCreds = { profileViewModel.cleanCreds() }
            )
        }
    }
}
