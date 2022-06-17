package bm.app.data.serde

import kotlinx.serialization.Serializable

@Serializable
data class PhoneVerificationResponse(val success: Boolean, val message: String)
