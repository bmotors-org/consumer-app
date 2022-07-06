package bm.app.base

import androidx.compose.runtime.mutableStateOf

class NavLogicState {
    private var verified = mutableStateOf(false)
    private var sessionID = mutableStateOf("")
    private var phoneNumber = mutableStateOf("")
    private var name = mutableStateOf("")
    private var email = mutableStateOf("")

    operator fun component1() = verified
    operator fun component2() = sessionID
    operator fun component3() = phoneNumber
    operator fun component4() = name
    operator fun component5() = email
}