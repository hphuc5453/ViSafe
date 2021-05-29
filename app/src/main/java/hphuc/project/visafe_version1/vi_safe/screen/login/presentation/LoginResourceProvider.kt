package hphuc.project.visafe_version1.vi_safe.screen.login.presentation

import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class LoginResourceProvider(mvpActivity: MvpActivity) : AndroidResourceProvider(mvpActivity) {
    fun getTextTitle(): String{
        return resourceManager.getString(R.string.text_sign_in)
    }

    fun getTextPleaseEnterPhoneNumber(): String {
        return resourceManager.getString(R.string.text_please_enter_phone_number)
    }

    fun getTextErrorEmptyPass(): String {
        return resourceManager.getString(R.string.text_error_empty_pass)
    }

    fun getTextPhoneIncorrect(): String {
        return resourceManager.getString(R.string.text_phone_incorrect)
    }
}