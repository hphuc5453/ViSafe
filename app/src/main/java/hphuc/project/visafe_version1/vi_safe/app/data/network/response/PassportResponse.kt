package hphuc.project.visafe_version1.vi_safe.app.data.network.response

import com.google.gson.annotations.SerializedName
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.base.BaseResponse

data class PassportResponse(
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("department_name")
    val departmentName: String,
    @SerializedName("employee_id")
    val employeeId: Int,
    @SerializedName("language_id")
    val languageId: Int,
    @SerializedName("mobile")
    var mobile: Any,
    @SerializedName("position_name")
    val positionName: String? = "",
    @SerializedName("rabbit_info")
    val rabbitInfo: RabbitInfo,
    @SerializedName("token")
    var token: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("default_map_style")
    val defaultMapStyle: String,
    @SerializedName("default_lat")
    val defaultLat: Double,
    @SerializedName("default_lon")
    val defaultLon: Double,
    @SerializedName("notification_hide_flag")
    var notificationHideFlag: Boolean,
    @SerializedName("notification_new_app_version_flag")
    var notificationNewAppVersionFlag: Boolean,
    @SerializedName("nickname")
    var nickname: String
): BaseResponse() {

    data class RabbitInfo(
        @SerializedName("CHATTING_EXCHANGE")
        val chattingExchange: String,
        @SerializedName("CHATTING_QUEUE")
        val chattingQueue: String,
        @SerializedName("RABBIT_HOST")
        val rabbitHost: String,
        @SerializedName("RABBIT_PASS")
        val rabbitPass: String,
        @SerializedName("RABBIT_PORT")
        val rabbitPort: Int,
        @SerializedName("RABBIT_USER")
        val rabbitUser: String,
        @SerializedName("RABBIT_VHOT")
        val rabbitVhost: String
    )
}