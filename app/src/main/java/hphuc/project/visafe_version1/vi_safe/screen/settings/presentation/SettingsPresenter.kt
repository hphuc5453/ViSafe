package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation

import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator
import hphuc.project.visafe_version1.vi_safe.screen.settings.domain.SettingsMapper

class SettingsPresenter(private val mResource: SettingsResourceProvider, private val screenNavigator: AndroidScreenNavigator): SettingsContract.Presenter() {
    override fun getData() {
        view?.showData(SettingsMapper(mResource).map(""))
    }

    override fun logout() {
        ConfigUtil.savePassport(null)
        screenNavigator.gotoLoginActivity()
    }
}