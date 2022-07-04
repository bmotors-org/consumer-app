package bm.app.screens.profile.api.network

import bm.app.ktor.KtorHttpClient
import bm.app.screens.profile.api.network.data.MergeEmailReq
import bm.app.screens.profile.api.network.data.MergeEmailRes
import bm.app.screens.profile.api.network.data.MergeNameReq
import bm.app.screens.profile.api.network.data.MergeNameRes
import bm.app.screens.profile.api.network.resources.Profile
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.*

class Network {
    suspend fun mergeName(name: String, sessionID: String): MergeNameRes {
        return try {
            KtorHttpClient.post(resource = Profile.Name()) {
                header("Authorization", sessionID)
                setBody(MergeNameReq(name))
            }
            MergeNameRes(success = true, message = "Merge name success")
        } catch (cause: Exception) {
            println(cause)
            MergeNameRes(success = false, message = "Merge name failed")
        }
    }

    suspend fun mergeEmail(email: String, sessionID: String): MergeEmailRes {
        return try {
            KtorHttpClient.post(resource = Profile.Email()) {
                header("Authorization", sessionID)
                setBody(MergeEmailReq(email))
            }
            MergeEmailRes(success = true, message = "Merge email success")
        } catch (cause: Exception) {
            println(cause)
            MergeEmailRes(success = false, message = "Merge email failed")
        }
    }
}
