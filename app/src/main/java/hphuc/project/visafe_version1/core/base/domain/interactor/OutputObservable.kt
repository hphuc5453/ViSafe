package hphuc.project.visafe_version1.core.base.domain.interactor

class OutputObservable<O> : DefaultObserver<O>() {
    var output: O? = null
    var exception: Throwable? = null
    override fun onNext(data: O) {
        super.onNext(data)
        output = data
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        this.exception = throwable

    }
}
