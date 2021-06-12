package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import android.graphics.drawable.Drawable
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class MainResourceProvider(mvpActivity: MvpActivity) : AndroidResourceProvider(mvpActivity) {
    fun getAccessMapbox(): String {
        return resourceManager.getString(R.string.mapbox_access_token)
    }

    fun getTextRequirePermission(): String {
        return resourceManager.getString(R.string.mgs_open_location)
    }

    fun getIconArrowDown(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_arrow_down_white)
    }

    fun getIconArrowUp(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_arrow_up)
    }

    fun getIconMenuAccidentDefault(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_accident_default)
    }

    fun getIconMenuAccidentActive(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_accident_accident_active)
    }

    fun getIconMenuCrimeDefault(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_accident_crime_default)
    }

    fun getIconMenuCrimeActive(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_crime_active)
    }

    fun getIconMenuDisasterDefault(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_accident_disaster_default)
    }

    fun getIconMenuDisasterActive(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_disaster_active)
    }

    fun getIconMenuVehicleDefault(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_accident_vehicle_default)
    }

    fun getIconMenuVehicleActive(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_vehicle_active)
    }

    fun getIconMenuAccident(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_accident)
    }

    fun getIconMenuVehicleMini(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_vehicle_mini)
    }

    fun getIconMenuCrimeMini(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_crime_mini)
    }

    fun getIconMenuDisasterMini(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_disaster_mini)
    }

    fun getIconMenuAccidentMini(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_menu_accident_mini)
    }

    fun getIconNotifyQuickly(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_notify_sent_urgent)
    }

    fun getIconNotifyAccident(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_notify_sent_accident)
    }

    fun getIconNotifyCrime(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_notify_sent_crime)
    }

    fun getIconNotifyDisaster(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_notify_sent_disaster)
    }

    fun getIconNotifyVehicle(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_notify_sent_vehicle)
    }
}