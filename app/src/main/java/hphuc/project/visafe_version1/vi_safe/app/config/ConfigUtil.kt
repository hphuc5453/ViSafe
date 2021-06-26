package hphuc.project.visafe_version1.vi_safe.app.config

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.config.ConfigSaver
import hphuc.project.visafe_version1.core.base.config.PaperConfigSaverImpl
import hphuc.project.visafe_version1.vi_safe.app.common.SettingsRoleData
import kotlinex.boolean.getValueOrDefault
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.PassportRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.PassportResponse

class ConfigUtil {
    companion object {

        @JvmStatic
        val passport: PassportResponse?
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_PASSPORT)
            }
        val passportRequest: PassportRequest?
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_KEY_LOGIN)
            }

        val isFirstLoginApp: Boolean
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                val isFirstLoginApp =
                    configSaver.get<Boolean>(ConfigSaver.CONFIG_SETTING_SAVED_IS_FIRST_LOGIN_APP)
                return isFirstLoginApp.getValueOrDefault()
            }

        val pushNotificationToken: String?
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get<String>(ConfigSaver.CONFIG_SETTING_PUSH_TOKEN)
            }

        val isFirstTimeLogin: Boolean
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get<Boolean>(ConfigSaver.CONFIG_SETTING_FIRST_TIME_LOGIN)
                    .getValueOrDefault()
            }

        val listContacts: MutableList<ViewModel>?
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_LIST_CONTACTS)
            }

        val listSupport: MutableList<ViewModel>?
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_LIST_SUPPORT)
            }

        val listRoleSettings: MutableList<SettingsRoleData>
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_LIST_ROLE_SETTINGS)
            }

        val mapStyle: String?
            get() {
                val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
                return configSaver.get(ConfigSaver.CONFIG_SETTING_KEY_MAP_STYLE)
            }

        @JvmStatic
        fun savePushToken(token: String) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_PUSH_TOKEN, token)
        }

        @JvmStatic
        fun saveListContacts(mutableList: MutableList<ViewModel>) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_LIST_CONTACTS, mutableList)
        }

        @JvmStatic
        fun saveListSupport(mutableList: MutableList<ViewModel>) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_LIST_SUPPORT, mutableList)
        }

        @JvmStatic
        fun saveListRoleSettings(mutableList: MutableList<SettingsRoleData>) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_LIST_ROLE_SETTINGS, mutableList)
        }

        @JvmStatic
        fun saveSupport(model: ViewModel) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_LIST_SUPPORT, model)
        }



        @JvmStatic
        fun savePassport(passportResponse: PassportResponse?) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            configSaver.save(ConfigSaver.CONFIG_SETTING_PASSPORT, passportResponse)
        }

        @JvmStatic
        fun saveIsFirstLoginApp(isFirstLoginApp: Boolean) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            configSaver.save(ConfigSaver.CONFIG_SETTING_SAVED_IS_FIRST_LOGIN_APP, isFirstLoginApp)
        }

        @JvmStatic
        fun savePassportRequest(request: PassportRequest) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_KEY_LOGIN, request)
        }

        @JvmStatic
        fun saveIsFirstTimeLogin(isFirstTimeLogin: Boolean?) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_FIRST_TIME_LOGIN, isFirstTimeLogin)
        }

        @JvmStatic
        fun saveMapStyle(style: String) {
            val configSaver = PaperConfigSaverImpl(ConfigSaver.CONFIG_PAGER)
            return configSaver.save(ConfigSaver.CONFIG_SETTING_KEY_MAP_STYLE, style)
        }
    }


}