package bm.app.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import bm.app.data.utils.categoryList

fun handleClick(navController: NavController, categoryIndex: Int) {
    navController.navigate(
        route = "services?categoryName=${
        categoryList[categoryIndex].name
        }"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 144.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(24.dp)
    ) {
        items(categoryList.size) { idx ->
            ElevatedCard(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 2.dp
                ),
                onClick = {
                    handleClick(
                        navController = navController,
                        categoryIndex = idx
                    )
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(
                            id = categoryList[idx].logo.id
                        ),
                        contentDescription = categoryList[idx].logo.desc,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = categoryList[idx].name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
