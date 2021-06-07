package hphuc.project.visafe_version1.vi_safe.screen.home.presentation

import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpPresenter
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView

interface HomeContract {
    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
        fun showError(content: String, onActionAccept: OnActionNotify? = null)
    }

    abstract class Presenter : MvpPresenter<View>()
}