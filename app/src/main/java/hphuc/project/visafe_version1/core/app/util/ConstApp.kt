package hphuc.project.visafe_version1.core.app.util

class ConstApp {
    companion object {
        val regexIPv4 = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$".toRegex()
        val regexNum = "[0-9]+".toRegex()
        const val emailIntentType = "vnd.android.cursor.dir/email"
        const val emailManager = "doanthanhduong11031990@gmail.com"
        const val emailEmployee = "leducanh.bkit10@gmail.com"
        const val textTypeEatin = "[E]"
        const val textTypeTakeaway = "[T]"
        const val textTypeDelivery = "[D]"
        const val colorDefault = "#ffa000"
        const val MAX_VALUE_INT: Int = 2147483647
        const val WIDTH_DIALOG_TABLET = 380
        const val MAX_CUSTOMER = 999
        const val OFFLINE = "OFFLINE"

        const val ERROR_CODE_DEFAULT = -1
        const val ERROR_CODE_UNKNOWN_HOST = 400
        const val ERROR_CODE_HTTP = 401
        const val ERROR_CODE_CONNECT = 402
        const val ERROR_TOKEN_EXPIRES = 103
        const val ERROR_OTP_EXPIRES = 106
        const val ERROR_OTP_HEADER_NOT_FOUND = 206
        const val JOB_ID: Int = 1
        const val TIME_WEEK: Long = 7 * 24 * 60 * 60 * 1000


    }
}