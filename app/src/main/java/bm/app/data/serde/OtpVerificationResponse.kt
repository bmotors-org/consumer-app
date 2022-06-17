package bm.app.data.serde

import kotlinx.serialization.Serializable

@Serializable
data class OtpVerificationResponse(
    val success: Boolean,
    val message: String,
    val token: String
)
