package bm.app.screens.service.api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource(path = "/auth")
class Auth {
    @Serializable
    @Resource(path = "verify-phone-number")
    class VerifyPhoneNumber(val parent: Auth = Auth())

    @Serializable
    @Resource(path = "verify-otp")
    class VerifyOtp(val parent: Auth = Auth())
}