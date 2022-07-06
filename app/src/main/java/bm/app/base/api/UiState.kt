package bm.app.base.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import bm.app.base.NavLogicState

class UiState {
    @Composable
    fun rememberNavLogicState() = remember {
        NavLogicState()
    }
}