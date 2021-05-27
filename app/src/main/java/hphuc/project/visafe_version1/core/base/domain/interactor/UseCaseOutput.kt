package hphuc.project.visafe_version1.core.base.domain.interactor

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import hphuc.project.visafe_version1.core.base.domain.exception.EmptyOutputException
import hphuc.project.visafe_version1.core.base.domain.interactor.*


abstract class UseCaseOutput<Output> internal constructor(private val useCaseExecution: UseCaseExecution) {
    private var disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Builds an [Observable] which will be used when executing the current [UseCaseOutput].
     */
    internal abstract fun buildUseCaseObservable(): Observable<Output>

    /**
     * Executes the current use case.
     *
     * @param observer [DisposableObserver] which will be listening to the observable build
     * by [.buildUseCaseObservable] ()} method.
     * @param input Parameters (Optional) used to build/executeAsync this use case.
     */
    fun executeAsync(resultListener: ResultListener<Output>): UseCaseTask {
        val observer: DefaultObserver<Output> = DefaultObserver()
        observer.addResultListener(LogResultListener())
        observer.addResultListener(ViewResponseResultListener(resultListener))
        val observable = this.buildUseCaseObservable()
                .subscribeOn(useCaseExecution.execution)
                .observeOn(useCaseExecution.postExecution)
        val disposable = observable.subscribeWith(observer)
        addDisposable(disposable)
        return UseCaseTask(disposable)
    }

    @Throws(Throwable::class)
    fun execute(): Output {
        val outputObservable = OutputObservable<Output>()
        outputObservable.addResultListener(LogResultListener())
        this.buildUseCaseObservable().subscribeWith(outputObservable)
        if (outputObservable.output != null) {
            return outputObservable.output!!
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