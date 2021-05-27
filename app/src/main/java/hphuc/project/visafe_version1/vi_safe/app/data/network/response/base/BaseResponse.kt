package hphuc.project.visafe_version1.vi_safe.app.data.network.response.base

import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("success")
    open var success: Boolean = false

    @SerializedName("detail")
    open var detail: String = ""

    @SerializedName("code")
    var code: String = ""

    @SerializedName("total_page")
    var totalPage: Int = 0

    @SerializedName("total_records")
    val total: Int = 0

    @SerializedName("total_container")
    val total_container: Int = 0

}