package hphuc.project.visafe_version1.vi_safe.screen.settings.domain

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.domain.mapper.Mapper
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.SettingsResourceProvider
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsHeaderItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsLineItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsMethodItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsPremiumItemViewModel
import kotlinex.string.getValueOrDefaultIsEmpty

class SettingsMapper(private val mResource: SettingsResourceProvider) :
    Mapper<String, MutableList<ViewModel>> {
    override fun map(input: String): MutableList<ViewModel> {
        val list = mutableListOf<ViewModel>()
        if (ConfigUtil.passport != null) {
            list.add(
                SettingsHeaderItemViewModel(
                    avatar = ConfigUtil.passport?.avatarUrl.getValueOrDefaultIsEmpty(),
                    name = ConfigUtil.passport?.fullName.getValueOrDefaultIsEmpty(),
                    phoneNumber = ConfigUtil.passport?.mobile.getValueOrDefaultIsEmpty()
                )
            )
        }

        list.add(SettingsMethodItemViewModel(
            icon = mResource.getIconRoleSettings(),
            name = mResource.getTextRoleSettings(),
            type = SettingsMethodItemViewModel.Type.ROLES.value
        ))
        list.add(SettingsMethodItemViewModel(
            icon = mResource.getIconAlarmSettings(),
            name = mResource.getTextAlarmSettings(),
            type = SettingsMethodItemViewModel.Type.ALARM.value
        ))
        list.add(SettingsMethodItemViewModel(
            icon = mResource.getIconRegisterSettings(),
            name = mResource.getTextRegisterSettings(),
            type = SettingsMethodItemViewModel.Type.REGISTER.value
        ))
        list.add(SettingsMethodItemViewModel(
            icon = mResource.getIconRateSettings(),
            name = mResource.getTextRateSettings(),
            type = SettingsMethodItemViewModel.Type.RATE.value
        ))
        list.add(SettingsMethodItemViewModel(
            icon = mResource.getIconHelpSettings(),
            name = mResource.getTextHelpSettings(),
            type = SettingsMethodItemViewModel.Type.HELP.value
        ))
        list.add(SettingsLineItemViewModel())
        list.add(SettingsMethodItemViewModel(
            icon = mResource.getIconLogoutSettings(),
            name = mResource.getTextLogoutSettings(),
            type = SettingsMethodItemViewModel.Type.LOGOUT.value
        ))
        list.add(SettingsPremiumItemViewModel())
        return list
    }
}