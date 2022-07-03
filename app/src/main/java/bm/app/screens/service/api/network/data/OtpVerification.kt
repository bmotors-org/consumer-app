package bm.app.screens.service.api.network.data

import io.ktor.resources.* // ktlint-disable no-wildcard-imports
import kotlinx.serialization.Serializable

@Serializable
@Resource(path = "/verify-phone-number")
data class OtpVerification(val phoneNumber: String, val otpCode: String)
