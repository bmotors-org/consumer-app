package bm.app.screens.service.api.network.data

import kotlinx.serialization.Serializable

@Serializable
data class PhoneVerifyRes(val success: Boolean, val message: String)
