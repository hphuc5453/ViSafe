package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import android.location.LocationManager
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpPresenter
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView

interface MainContract {
    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
        fun showError(content: String)
        fun handleGetMap()
        fun handleAfterRequestPermission()
        fun showRequestPermission(permission: String): Boolean
        fun handlePermissionFail()
        fun showAlertMessageNoGps()

    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun downloadStyleMap()
        abstract fun checkLocation(manager: LocationManager)
        abstract fun registerAppPermission()
    }
}