package bm.app.screens.profile.api.network.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/profile")
class Profile {

    @Serializable
    @Resource("merge-name")
    class Name(val parent: Profile = Profile())

    @Serializable
    @Resource("merge-email")
    class Email(val parent: Profile = Profile())
}
