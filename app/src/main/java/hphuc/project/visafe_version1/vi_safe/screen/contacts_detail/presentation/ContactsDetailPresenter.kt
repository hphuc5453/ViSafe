package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation

import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.domain.ContactsDetailMapper

class ContactsDetailPresenter : ContactsDetailContract.Presenter() {
    override fun getData(extra: ContractsDetailDataIntent) {
        view?.showData(ContactsDetailMapper().map(extra))
    }
}