package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import android.location.LocationManager
import com.mapbox.mapboxsdk.Mapbox
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.screen.home_map.data.HomeMapDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.main.data.EventMenu
import kotlinex.boolean.getValueOrDefault

class MainPresenter(
    private val mvpActivity: MvpActivity,
    private val mResource: MainResourceProvider
) : MainContract.Presenter() {

    override fun handleEventMenu(event: EventMenu) {
        when {
            event.event != null -> {
                when (event.event) {
                    EventMenu.CategoryType.HOME -> {
                        view?.showHomeFragment()
                    }
                    EventMenu.CategoryType.CONTACTS -> {
                        view?.showContactListFragment()
                    }
                    EventMenu.CategoryType.LIVE -> {
                        view?.showLiveFragment()
                    }
                    EventMenu.CategoryType.CALL -> {
                        view?.showCallFragment()
                    }
                    EventMenu.CategoryType.SETTINGS -> {
                        view?.showSettingFragment()
                    }
                }
            }
            event.eventAction != null -> {
                when (event.eventAction) {
                    EventMenu.ActionType.SHOW_LOADING -> {
                        view?.showLoading()
                    }
                    EventMenu.ActionType.HIDE_LOADING -> {
                        view?.hideLoading()
                    }
//                    EventMenu.ActionType.BACK_COUNT->{
//                        view?.showLoading()
//                    }
                    else -> {}
                }
            }
            event.eventChildFragment != null -> {
                when (event.eventChildFragment) {
                    EventMenu.ChildFragment.HOME_MAP -> {
                        val extra = event.data as HomeMapDataIntent
                        view?.showHomeMapFragment(extra)
                    }
                    EventMenu.ChildFragment.NOTIFY -> {
                        view?.showNotifyFragment()
                    }
                }
            }
        }
    }

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