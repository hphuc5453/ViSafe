package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpPresenter
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView

interface MainContract {
    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
        fun showError(content: String)
    }

    abstract class Presenter : MvpPresenter<View>() {
    }
}