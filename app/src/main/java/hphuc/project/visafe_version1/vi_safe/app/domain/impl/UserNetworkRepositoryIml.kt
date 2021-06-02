package hphuc.project.visafe_version1.vi_safe.app.domain.impl

import hphuc.project.visafe_version1.vi_safe.app.domain.UserNetworkRepository
import hphuc.project.visafe_version1.vi_safe.app.network.ApiClient
import io.reactivex.Observable

class UserNetworkRepositoryIml : UserNetworkRepository {
    override fun getMapStyle(): Observable<String> {
        val service = ApiClient.getClientNotJson()
        return service.getMapStyle("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
    }

}