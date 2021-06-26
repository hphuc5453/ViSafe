package hphuc.project.visafe_version1.vi_safe.screen.settings_role.domain

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.domain.mapper.Mapper
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.SettingsRoleResourceProvider
import hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.model.SettingsRoleItemViewModel

class SettingsRoleMapper(private val mResource: SettingsRoleResourceProvider) :
    Mapper<String, MutableList<ViewModel>> {
    override fun map(input: String): MutableList<ViewModel> {
        val list = mutableListOf<ViewModel>()
        if (!ConfigUtil.listRoleSettings.isNullOrEmpty()) {
            ConfigUtil.listRoleSettings.forEach {
                list.add(
                    SettingsRoleItemViewModel(
                        typeAccident = it.accidentType,
                        type = it.type,
                        isChoose = it.isChoose,
                        name = getNameWithType(it.type)
                    )
                )
            }
        }
        return list
    }

    private fun getNameWithType(type: String): String {
        when(type){
            AppConstants.ROLE_POLICY->{
                return mResource.getTextRolePolicy()
            }
            AppConstants.ROLE_STREET_BODYGUARD->{
                return mResource.getTextRoleStreetBodyguard()
            }
            AppConstants.ROLE_FIRE_FIGHT->{
                return mResource.getTextRoleFirefight()
            }
            AppConstants.ROLE_CIVIL_DEFENSE->{
                return mResource.getTextRoleCivilDefense()
            }
            else->{
                return mResource.getTextRoleHospital()
            }
        }
    }

}