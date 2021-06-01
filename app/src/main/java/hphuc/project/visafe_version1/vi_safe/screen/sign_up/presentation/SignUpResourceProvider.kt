package hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation

import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class SignUpResourceProvider(mvpActivity: MvpActivity): AndroidResourceProvider(mvpActivity) {
    fun getTextTitle(): String{
        return resourceManager.getString(R.string.text_sign_up_for_login)
    }
}