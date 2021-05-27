package hphuc.project.visafe_version1.vi_safe.app.data.network.request

import com.google.gson.annotations.SerializedName

data class PassportRequest(
    @SerializedName("username") var user: String,
    @SerializedName("password") var pass: String
)