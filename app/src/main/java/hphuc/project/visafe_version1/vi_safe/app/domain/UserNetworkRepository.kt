package hphuc.project.visafe_version1.vi_safe.app.domain

import io.reactivex.Observable

interface UserNetworkRepository {
    fun getMapStyle(): Observable<String>
}