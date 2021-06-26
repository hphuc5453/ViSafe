package hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation

import android.graphics.drawable.Drawable
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class SettingsRoleResourceProvider(mvpActivity: MvpActivity): AndroidResourceProvider(mvpActivity) {
    fun getTextTitle(): String {
        return resourceManager.getString(R.string.text_settings_role)
    }

    fun getIconChecked(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_checked_rounder_blue)
    }

    fun getIconUnCheck(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_un_check_rounder_blue)
    }

}