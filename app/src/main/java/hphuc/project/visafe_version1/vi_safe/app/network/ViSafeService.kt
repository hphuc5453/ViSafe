package hphuc.project.visafe_version1.vi_safe.app.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface ViSafeService {
    companion object {
        const val VERSION = "v1"
    }

    @GET
    fun getMapStyle(@Url fileURL: String): Observable<String>
}