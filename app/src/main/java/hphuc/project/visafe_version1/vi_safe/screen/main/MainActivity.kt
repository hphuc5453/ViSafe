package hphuc.project.visafe_version1.vi_safe.screen.main

import android.os.Bundle
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.screen.main.presentation.MainView

class MainActivity : MvpActivity() {
    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return MainView(this, MainView.ViewCreator(this, null))
    }

    override fun getBackgroundScreen(): Int {
        return R.drawable.background
    }
}