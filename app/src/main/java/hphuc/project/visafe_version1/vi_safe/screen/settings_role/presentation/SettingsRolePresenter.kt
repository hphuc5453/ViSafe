package hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation

import hphuc.project.visafe_version1.vi_safe.screen.settings_role.domain.SettingsRoleMapper

class SettingsRolePresenter(private val mResource: SettingsRoleResourceProvider): SettingsRoleContract.Presenter() {
    override fun getData() {
        view?.showData(SettingsRoleMapper(mResource).map(""))
    }
}