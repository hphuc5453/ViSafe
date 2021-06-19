package hphuc.project.visafe_version1.vi_safe.screen.settings

import android.os.Bundle
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.SettingsView

class SettingsFragment : MvpFragment() {
    companion object {
        val TAG = SettingsFragment::class.java.simpleName
    }

    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return SettingsView(getMvpActivity(), SettingsView.ViewCreator(getMvpActivity(), null))
    }
}