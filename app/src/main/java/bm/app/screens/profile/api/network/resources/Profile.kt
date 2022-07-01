package bm.app.screens.profile.api.network.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/profile")
class Profile {

    @Serializable
    @Resource("name")
    class Name
}
