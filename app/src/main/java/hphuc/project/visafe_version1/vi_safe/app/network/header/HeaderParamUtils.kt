package hphuc.project.visafe_version1.vi_safe.app.network.header

import com.orhanobut.logger.Logger
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import java.text.SimpleDateFormat
import java.util.*

class HeaderParamUtils {
    companion object {
        @JvmStatic
        fun getListHeader(method: String, url: String, requestBody: String?): Map<String, String> {
//            Log.d("HeaderParamUtils", requestBody + "")
            val headers = mutableMapOf<String, String>()
            headers[HeaderConst.CONNECTION] = HeaderConst.CONNECTION_VALUE
            headers[HeaderConst.UNIPRIME_DEVICE] = getAccessDevice()
            headers[HeaderConst.CACHE] =HeaderConst.CACHE_VALUE
            headers[HeaderConst.ACCEPT] = HeaderConst.ACCEPT_VALUE
//            headers[HeaderConst.CONTENT_ENCODE] = HeaderConst.CONTENT_ENCODE_VALUE
            val isFirstLogin = ConfigUtil.isFirstLoginApp

//            Log.d("HeaderParamUtils", isFirstLogin.toString())
            if (isFirstLogin) {
                val passport = ConfigUtil.passport
                passport?.let { useModel ->
                    val userToken = useModel.token
                    if (userToken.isNotEmpty()) {
                        headers[HeaderConst.AUTHOR] = "Bearer $userToken"
                    }
                }
            }
//            headers[HeaderConst.ACCEPT] = HeaderConst.ACCEPT_VALUE
            headers[HeaderConst.X_CONTENT_TYPE] = HeaderConst.X_CONTENT_TYPE_VALUE
            return headers
        }

        private fun getAccessDevice(): String {
            val temp: String
            val calendar = Calendar.getInstance()
            val second = calendar.get(Calendar.SECOND)
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
            temp = sdf.format(calendar.time)
            var timeNoSecond = calendar.timeInMillis / 1000
            try {
                val d = sdf.parse(temp)
                timeNoSecond = d.time / 1000
            } catch (ignored: Exception) {
            }

            if (second < 30) {
                timeNoSecond += 29
            } else {
                timeNoSecond += 59
            }

            try {
                val secretKey = when {
                    else -> HeaderConst.SECRET_KEY_UAT
                }
                val timeTest = "" + timeNoSecond
                return ""
            } catch (e: Exception) {
                Logger.e(e.message)
            }

            return ""
        }
    }
}