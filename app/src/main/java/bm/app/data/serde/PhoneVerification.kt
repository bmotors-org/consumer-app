package bm.app.data.serde

import io.ktor.resources.* // ktlint-disable no-wildcard-imports
import kotlinx.serialization.* // ktlint-disable no-wildcard-imports

@Serializable
@Resource(path = "/verify-phone-number")
data class PhoneVerification(val phoneNumber: String)
