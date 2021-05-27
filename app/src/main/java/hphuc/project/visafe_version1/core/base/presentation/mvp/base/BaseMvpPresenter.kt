package hphuc.project.visafe_version1.core.base.presentation.mvp.base

interface BaseMvpPresenter<in V: MvpView> {

    fun attachView(view: V)

    fun detachView()
}