package hphuc.project.visafe_version1.vi_safe.screen.home.presentation

import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class HomeResourceProvider(mvpActivity: MvpActivity): AndroidResourceProvider(mvpActivity){
    fun getTextPleaseChoosePersonSupport(): String{
        return resourceManager.getString(R.string.text_please_choose_person_support)
    }
}