package hphuc.project.visafe_version1.vi_safe.screen.notify.presentation

import hphuc.project.visafe_version1.vi_safe.screen.notify.domain.NotifyMapper

class NotifyPresenter: NotifyContract.Presenter() {
    override fun getData() {
        view?.showData(NotifyMapper().map(""))
    }
}