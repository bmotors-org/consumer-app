package bm.app.screens.service.api.data

import kotlinx.serialization.Serializable

@Serializable
data class OtpVerificationRes(
    val success: Boolean,
    val message: String,
    val sessionID: String?,
    val name: String?,
    val email: String?,
)
