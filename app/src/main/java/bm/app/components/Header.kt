package bm.app.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bm.app.R

@Composable
fun Header() {
    val companyLogo = painterResource(id = R.drawable.logo)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        Icon(
            painter = companyLogo,
            contentDescription = "Icon for the app",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f)
        )
        Text(
            text = "Bangla Motors",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
