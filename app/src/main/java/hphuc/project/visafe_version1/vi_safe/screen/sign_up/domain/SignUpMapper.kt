package hphuc.project.visafe_version1.vi_safe.screen.sign_up.domain

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.domain.mapper.Mapper
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.SignUpResourceProvider
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.model.RoleItemViewModel

class SignUpMapper(private val mResource: SignUpResourceProvider) :
    Mapper<String, MutableList<ViewModel>> {
    override fun map(input: String): MutableList<ViewModel> {
        val list = mutableListOf<ViewModel>()
        list.add(
            RoleItemViewModel(
                type = AppConstants.POLICY,
                name = mResource.getTextRolePolicy(),
                isChoose = true
            )
        )
        list.add(
            RoleItemViewModel(
                type = AppConstants.CIVIL_DEFENSE,
                name = mResource.getTextRoleCivilDefense()
            )
        )
        list.add(
            RoleItemViewModel(
                type = AppConstants.STREET_BODYGUARD,
                name = mResource.getTextRoleStreetBodyguard()
            )
        )
        list.add(
            RoleItemViewModel(
                type = AppConstants.HOSPITAL,
                name = mResource.getTextRoleHospital()
            )
        )
        list.add(
            RoleItemViewModel(
                type = AppConstants.FIRE_FIGHT,
                name = mResource.getTextRoleFirefight()
            )
        )

        return list
    }
}