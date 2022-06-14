package bm.app.data.serde
import kotlinx.serialization.Serializable

@Serializable
data class JwtToken(val token: String)
