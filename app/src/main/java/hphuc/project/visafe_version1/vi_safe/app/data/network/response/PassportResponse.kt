package hphuc.project.visafe_version1.vi_safe.app.data.network.response

import com.google.gson.annotations.SerializedName
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.base.BaseResponse

data class PassportResponse(
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("mobile")
    var mobile: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("token")
    var token: String ="123456780abcdefhhik"
): BaseResponse()