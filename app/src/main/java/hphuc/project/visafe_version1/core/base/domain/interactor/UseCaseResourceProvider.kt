package hphuc.project.visafe_version1.core.base.domain.interactor

import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.manager.AndroidResourceManager
import hphuc.project.visafe_version1.core.base.domain.provider.ResourceProvider

class UseCaseResourceProvider : ResourceProvider(AndroidResourceManager()) {
    fun getErrorMsg(errorCode: Int): String {
        return resourceManager.getString(R.string.warning_check_server_again)
    }

    fun getErrorMsg(urlResponse: String,errorMessage: String): String{
        return "$urlResponse - $errorMessage"
    }

    fun getErrorMsg(): String {
        return resourceManager.getString(R.string.error_system_msg)
    }

    fun getErrorMsgUnknownHostException(): String {
        return resourceManager.getString(R.string.error_internet_connect)
    }

    fun  getErrorMsg(errorMsg: String?): String {
        val errorMsgExt = if (errorMsg != null)  ": $errorMsg" else ""
        return resourceManager.getString(R.string.error_system_msg) + errorMsgExt
    }
}