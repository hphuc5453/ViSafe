package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpPresenter
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent

interface ListContactsContract {
    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
        fun showError(content: String, onActionAccept: OnActionNotify? = null)
        fun handleInitData(list: MutableList<ViewModel>)
    }

    abstract class Presenter : MvpPresenter<View>(){
        abstract fun getData()
        abstract fun gotoContactsDetailActivity(extra: ContractsDetailDataIntent)
    }
}