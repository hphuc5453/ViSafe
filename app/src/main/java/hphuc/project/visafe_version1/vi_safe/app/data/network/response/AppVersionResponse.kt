package hphuc.project.visafe_version1.vi_safe.app.data.network.response

import com.google.gson.annotations.SerializedName
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.base.BaseResponse

data class AppVersionResponse(
    @SerializedName("version") val version: String,
    @SerializedName("force_flag") val forceFlag: Boolean,
    @SerializedName("google_play") val googlePlay: String,
    @SerializedName("version_app") val versionApp: String
) : BaseResponse()