package hphuc.project.visafe_version1.vi_safe.screen.sign_up

import android.os.Bundle
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.SignUpView

class SignUpActivity : MvpActivity() {
    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return SignUpView(this, SignUpView.ViewCreator(this, null))
    }

    override fun getBackgroundScreen(): Int {
        return R.drawable.background
    }
}