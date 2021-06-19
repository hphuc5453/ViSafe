package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation

import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.domain.ListContactsMapper

class ListContactsPresenter(private val mvpActivity: MvpActivity, private val screenNavigator: AndroidScreenNavigator) : ListContactsContract.Presenter() {
    override fun getData() {
        view?.handleInitData(ListContactsMapper().map(""))
    }

    override fun gotoContactsDetailActivity(extra: ContractsDetailDataIntent) {
        screenNavigator.gotoContactsDetailActivity(extra)
    }
}