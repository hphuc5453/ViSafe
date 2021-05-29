package hphuc.project.visafe_version1.vi_safe.screen.login.presentation

import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpPresenter
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.LoginRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.PassportRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.PassportResponse

interface LoginContact {

    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
        fun showError(content: String)
        fun handleLogout()
        fun handleLoginWithPassport(passport: PassportResponse)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun logInApp(request: LoginRequest)
        abstract fun login(request: PassportRequest)
        abstract fun logout()
    }

}