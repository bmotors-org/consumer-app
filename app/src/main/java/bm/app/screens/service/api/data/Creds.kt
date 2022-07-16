package bm.app.screens.service.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Creds(
    val sessionID: String,
    val name: String,
    val email: String,
)
