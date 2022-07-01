package bm.app.screens.profile.api.network

import bm.app.ktor.KtorHttpClient
import bm.app.screens.profile.api.network.data.MergeNameReq
import bm.app.screens.profile.api.network.data.MergeNameRes
import bm.app.screens.profile.api.network.resources.Profile
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody

class Network {
    suspend fun mergeName(name: String, token: String): MergeNameRes {
        return try {
            KtorHttpClient.post(resource = Profile.Name()) {
                setBody(MergeNameReq(name, token))
            }
            MergeNameRes(success = true, message = "Merge name success")
        } catch (cause: Exception) {
            println(cause)
            MergeNameRes(success = false, message = "Merge name failed")
        }
    }
}
