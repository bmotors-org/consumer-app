package bm.app.screens.service.api.data

import kotlinx.serialization.Serializable

@Serializable
data class PhoneVerifyRes(val success: Boolean, val message: String)
