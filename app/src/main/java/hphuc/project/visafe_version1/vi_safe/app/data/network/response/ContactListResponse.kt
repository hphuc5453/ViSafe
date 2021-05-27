package hphuc.project.visafe_version1.vi_safe.app.data.network.response
import com.google.gson.annotations.SerializedName
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.base.BaseResponse


data class ContactListResponse(
    @SerializedName("list")
    val list: List<ContactTask>
): BaseResponse() {

    data class ContactTask(
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("company")
        val company: String,
        @SerializedName("department")
        val department: String,
        @SerializedName("email_1")
        val email1: String,
        @SerializedName("email_2")
        val email2: String,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("contact_id")
        val id: Int,
        @SerializedName("mobile_1")
        val mobile1: String,
        @SerializedName("mobile_2")
        val mobile2: String,
        @SerializedName("position")
        val position: String
    )

}