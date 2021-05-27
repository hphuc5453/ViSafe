package hphuc.project.visafe_version1.core.base.domain.interactor

import hphuc.project.visafe_version1.core.app.domain.excecutor.AndroidUseCaseExecution
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import hphuc.project.visafe_version1.core.app.util.ConstApp
import hphuc.project.visafe_version1.core.base.domain.exception.EmptyOutputException
import hphuc.project.visafe_version1.vi_safe.domain.ReLoginUseCase
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.PassportResponse

abstract class UseCase<in Input, Output> internal constructor(private val useCaseExecution: UseCaseExecution) {
    private var disposables: CompositeDisposable = CompositeDisposable()
    var calledAgain = false
    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    internal abstract fun buildUseCaseObservable(input: Input): Observable<Output>

    fun executeAsync(resultListener: ResultListener<Output>, input: Input): UseCaseTask {
        val observer: DefaultObserver<Output> = DefaultObserver()
        observer.addResultListener(LogResultListener())
        val observable = this.buildUseCaseObservable(input)
            .subscribeOn(useCaseExecution.execution)
            .observeOn(useCaseExecution.postExecution)
        if (calledAgain) {
            observer.addResultListener(ViewResponseResultListener(resultListener))
        } else {
            observer.addResultListener(ViewResponseCustomTokenResultListener(resultListener, this, input))
        }
        val disposable = observable.subscribeWith(observer)
        addDisposable(disposable)
        return UseCaseTask(disposable)
    }

    @Throws(Throwable::class)
    fun execute(input: Input): Output {
        val outputObservable = OutputObservable<Output>()
        outputObservable.addResultListener(LogResultListener())
        this.buildUseCaseObservable(input).subscribeWith(outputObservable)
        val data = outputObservable.output
        if (data != null) {
            if (!calledAgain && data is PassportResponse && (ConstApp.ERROR_OTP_EXPIRES.toString() == data.code
                        || ConstApp.ERROR_OTP_HEADER_NOT_FOUND.toString() == data.code
                        || ConstApp.ERROR_TOKEN_EXPIRES.toString() == data.code)) {
                val relogin = ReLoginUseCase(AndroidUseCaseExecution()).execute("")
                if (relogin.success) {
//                    relogin.userId = 207
                    ConfigUtil.savePassport(relogin)
                    ConfigUtil.saveIsFirstLoginApp(true)
                }
                this.calledAgain = true
                return this.execute(input)
            }
            return data
        }
        throw if (outputObservable.exception != null) {
            outputObservable.exception!!
        } else EmptyOutputException()
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun cancel() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
        disposables = CompositeDisposable()
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}