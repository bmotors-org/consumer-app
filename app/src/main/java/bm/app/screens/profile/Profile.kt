package bm.app.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bm.app.screens.profile.components.*

@Composable
fun Profile(
    sessionID: String,
    name: String,
    setName: (String) -> Unit,
    phoneNumber: String,
    email: String,
    setEmail: (String) -> Unit,
    profileViewModel: ProfileViewModel = viewModel()
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        Header()
        Spacer(modifier = Modifier.size(16.dp))
        Summary(
            phoneNumber = phoneNumber
        )
        NameField(
            sessionID = sessionID,
            name = name,
            setName = setName,
            mergeName = { name, sessionID ->
                profileViewModel.mergeName(name, sessionID)
            },
            storeName = { name ->
                profileViewModel.storeName(name)
            }
        )

        EmailField(
            sessionID = sessionID,
            email = email,
            setEmail = setEmail,
            mergeEmail = { email, sessionID ->
                profileViewModel.mergeEmail(email, sessionID)
            },
            storeEmail = { email ->
                profileViewModel.storeEmail(email)
            }
        )

        Spacer(modifier = Modifier.size(24.dp))

        LogoutButton(
            cleanCreds = { profileViewModel.cleanCreds() }
        )
    }
}
