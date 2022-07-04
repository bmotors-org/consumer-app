package bm.app.screens.profile.api.network.data

import kotlinx.serialization.Serializable

@Serializable
data class MergeEmailRes(
    val success: Boolean,
    val message: String
)
