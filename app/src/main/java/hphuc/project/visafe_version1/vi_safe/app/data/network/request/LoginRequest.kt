package hphuc.project.visafe_version1.vi_safe.app.data.network.request

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @SerializedName("device_token")
    var token: String)