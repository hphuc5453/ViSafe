package hphuc.project.visafe_version1.vi_safe.screen.settings_role

import android.os.Bundle
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.SettingsRoleView

class SettingsRoleFragment: MvpFragment(){

    companion object {
        val TAG = SettingsRoleFragment::class.java.simpleName
    }
    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return SettingsRoleView(getMvpActivity(), SettingsRoleView.ViewCreator(getMvpActivity(), null))
    }
}