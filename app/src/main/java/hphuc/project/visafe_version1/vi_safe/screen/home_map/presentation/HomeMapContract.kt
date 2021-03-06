package hphuc.project.visafe_version1.vi_safe.screen.home_map.presentation

import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpPresenter
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView

interface HomeMapContract {
    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
        fun showError(content: String)
        fun getOnMapReadyCallback(): OnMapReadyCallback
        fun loadMapView()
    }

    abstract class Presenter : MvpPresenter<View>()
}