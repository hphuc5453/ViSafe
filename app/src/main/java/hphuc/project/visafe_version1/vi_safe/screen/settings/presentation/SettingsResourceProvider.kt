package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation

import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class SettingsResourceProvider(mvpActivity: MvpActivity): AndroidResourceProvider(mvpActivity) {
    fun getTextTitle(): String {
        return resourceManager.getString(R.string.text_settings)
    }

    fun getIconRoleSettings(): Int{
        return R.drawable.ic_role_settings_white
    }

    fun getIconAlarmSettings(): Int{
        return R.drawable.ic_alarm_white
    }

    fun getIconRegisterSettings(): Int{
        return R.drawable.ic_footprints_white
    }

    fun getIconRateSettings(): Int{
        return R.drawable.ic_rate_white
    }

    fun getIconHelpSettings(): Int{
        return R.drawable.ic_help_white
    }

    fun getIconLogoutSettings(): Int{
        return R.drawable.ic_log_out_white
    }

    fun getTextRoleSettings(): String {
        return resourceManager.getString(R.string.text_role_settings)
    }

    fun getTextAlarmSettings(): String {
        return resourceManager.getString(R.string.text_alarm)
    }

    fun getTextRegisterSettings(): String {
        return resourceManager.getString(R.string.text_register_personal_profile_settings)
    }

    fun getTextRateSettings(): String {
        return resourceManager.getString(R.string.text_rate_us)
    }

    fun getTextHelpSettings(): String {
        return resourceManager.getString(R.string.text_help)
    }

    fun getTextLogoutSettings(): String {
        return resourceManager.getString(R.string.text_logout)
    }
}