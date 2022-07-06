package bm.app.screens.profile.api.network.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource(path = "/profile")
class Profile {
    @Serializable
    @Resource(path = "merge-name")
    class Name(val parent: Profile = Profile())

    @Serializable
    @Resource(path = "merge-email")
    class Email(val parent: Profile = Profile())
}
