package hphuc.project.visafe_version1.vi_safe.domain

import hphuc.project.visafe_version1.core.base.domain.interactor.UseCase
import hphuc.project.visafe_version1.core.base.domain.interactor.UseCaseExecution
import io.reactivex.Observable
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.PassportResponse
import hphuc.project.visafe_version1.vi_safe.app.domain.impl.UserNetworkRepositoryIml

class ReLoginUseCase constructor(useCaseExecution: UseCaseExecution) : UseCase<String, PassportResponse>(useCaseExecution) {
    override fun buildUseCaseObservable(input: String): Observable<PassportResponse> {
        return Observable.create{

        } //retrofit để call api
    }
}