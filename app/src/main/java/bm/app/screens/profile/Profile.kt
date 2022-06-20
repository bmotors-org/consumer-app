package bm.app.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import bm.app.screens.profile.components.Header
import bm.app.screens.profile.components.NameField
import bm.app.screens.profile.components.Summary

@Composable
fun Profile() {
    Column {
        Header()
        Summary()
        NameField()
    }
}
