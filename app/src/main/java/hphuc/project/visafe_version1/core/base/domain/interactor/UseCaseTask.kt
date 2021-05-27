package hphuc.project.visafe_version1.core.base.domain.interactor

import io.reactivex.disposables.Disposable

class UseCaseTask(private var disposable: Disposable) {

    fun cancel() {
        disposable.dispose()
    }
}