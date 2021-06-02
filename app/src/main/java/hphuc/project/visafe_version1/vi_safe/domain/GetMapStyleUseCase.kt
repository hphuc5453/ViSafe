package hphuc.project.visafe_version1.vi_safe.domain

import hphuc.project.visafe_version1.core.base.domain.interactor.UseCase
import hphuc.project.visafe_version1.core.base.domain.interactor.UseCaseExecution
import hphuc.project.visafe_version1.vi_safe.app.domain.impl.UserNetworkRepositoryIml
import io.reactivex.Observable

class GetMapStyleUseCase(useCaseExecution: UseCaseExecution) :
    UseCase<String, String>(useCaseExecution) {
    override fun buildUseCaseObservable(input: String): Observable<String> {
        return UserNetworkRepositoryIml().getMapStyle()
    }
}