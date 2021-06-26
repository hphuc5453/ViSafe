package hphuc.project.visafe_version1.vi_safe.screen.call_now.presentation

import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.domain.ListContactsMapper

class CallNowPresenter: CallNowContract.Presenter() {
    override fun getData() {
        view?.handleInitData(ListContactsMapper().map(""))
    }

    override fun gotoContactsDetailActivity(extra: ContractsDetailDataIntent) {
    }
}