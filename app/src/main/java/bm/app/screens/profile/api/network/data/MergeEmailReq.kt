package bm.app.screens.profile.api.network.data

import kotlinx.serialization.Serializable

@Serializable
data class MergeEmailReq(
    val email: String,
)
