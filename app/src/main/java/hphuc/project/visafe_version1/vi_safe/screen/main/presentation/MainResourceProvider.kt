package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import android.graphics.drawable.Drawable
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class MainResourceProvider (mvpActivity: MvpActivity): AndroidResourceProvider(mvpActivity){
    fun getAccessMapbox(): String {
        return resourceManager.getString(R.string.mapbox_access_token)
    }

    fun getTextRequirePermission(): String {
        return resourceManager.getString(R.string.mgs_open_location)
    }

    fun getIconArrowDown(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_arrow_down_white)
    }

    fun getIconArrowUp(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_arrow_up)
    }
}