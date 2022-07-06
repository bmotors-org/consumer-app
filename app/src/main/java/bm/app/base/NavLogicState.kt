package bm.app.base

import androidx.compose.runtime.mutableStateOf

class NavLogicState {
    private var verifiedState = mutableStateOf(false)
    private var sessionIDState = mutableStateOf("")
    private var phoneNumberState = mutableStateOf("")
    private var nameState = mutableStateOf("")
    private var emailState = mutableStateOf("")

    var verified: Boolean
        get() = verifiedState.value
        set(value) {
            verifiedState.value = value
        }

    var sessionID: String
        get() = sessionIDState.value
        set(value) {
            sessionIDState.value = value
        }

    var phoneNumber: String
        get() = phoneNumberState.value
        set(value) {
            phoneNumberState.value = value
        }

    var name: String
        get() = nameState.value
        set(value) {
            nameState.value = value
        }

    var email: String
        get() = emailState.value
        set(value) {
            emailState.value = value
        }
}