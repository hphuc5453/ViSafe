package hphuc.project.visafe_version1.core.base.domain.interactor

import io.reactivex.observers.DisposableObserver

/**
 * Default [DisposableObserver] base class to be used whenever you want default error handling.
 */
open class DefaultObserver<T> : DisposableObserver<T>() {
    private val resultListeners = mutableListOf<RawResultListener<T>>()

    fun addResultListener(resultListener: RawResultListener<T>) {
        resultListeners.add(resultListener)
    }

    override fun onNext(data: T) {
        fireSuccess(data)
    }

    override fun onComplete() {
        fireDone()
    }

    override fun onError(throwable: Throwable) {
        fireFail(throwable)
        fireDone()
    }

    private fun fireSuccess(data: T) {
        resultListeners.forEach { resultListener -> resultListener.success(data) }
    }

    private fun fireDone() {
        resultListeners.forEach { resultListener -> resultListener.done() }
    }

    private fun fireFail(throwable: Throwable) {
        resultListeners.forEach { resultListener -> resultListener.fail(throwable) }
    }
}
