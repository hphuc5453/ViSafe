package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import android.location.LocationManager
import com.mapbox.mapboxsdk.Mapbox
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import kotlinex.boolean.getValueOrDefault

class MainPresenter(
    private val mvpActivity: MvpActivity,
    private val mResource: MainResourceProvider
) : MainContract.Presenter() {

    override fun downloadStyleMap() {
        Mapbox.getInstance(mvpActivity, mResource.getAccessMapbox())
        ConfigUtil.saveMapStyle("mapbox://styles/mapbox/dark-v10")
        view?.handleGetMap()
    }

    override fun registerAppPermission() {
        for (permission in AppConstants.PERMISSIONS_IN_APP) {
            if (!view?.showRequestPermission(permission).getValueOrDefault()) {
            }
        }
        view?.handleAfterRequestPermission()
    }

    override fun checkLocation(manager: LocationManager) {
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            view?.showAlertMessageNoGps()
        }
    }
}