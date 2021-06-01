package hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpPresenter
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView

interface SignUpContract {
    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
        fun showError(content: String)
        fun showData(list: MutableList<ViewModel>)
        fun handleSignUp()
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun getData()
        abstract fun signUp()
    }
}