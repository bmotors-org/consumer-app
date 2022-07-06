package bm.app.base

import androidx.compose.runtime.mutableStateOf

class NavLogicState {
    var verified = mutableStateOf(false)
    var sessionID = mutableStateOf("")
    var phoneNumber = mutableStateOf("")
    var name = mutableStateOf("")
    var email = mutableStateOf("")
}