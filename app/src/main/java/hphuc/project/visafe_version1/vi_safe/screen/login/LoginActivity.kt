package hphuc.project.visafe_version1.vi_safe.screen.login

import android.os.Bundle
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.screen.login.presentation.LoginView

class LoginActivity : MvpActivity() {

    lateinit var view: LoginView

    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        view = LoginView(
            this,
            LoginView.ViewCreator(
                this,
                null
            )
        )
        return view
    }

    override fun getBackgroundScreen(): Int {
        return R.drawable.background
    }
}