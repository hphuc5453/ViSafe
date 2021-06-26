package hphuc.project.visafe_version1

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.module.http.HttpRequestUtil
import hphuc.project.visafe_version1.core.app.common.AppConfigs
import hphuc.project.visafe_version1.core.base.manager.LocaleManager
import hphuc.project.visafe_version1.vi_safe.app.common.AccidentType
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.app.common.SettingsRoleData
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.network.HttpClient
import io.paperdb.Paper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        app = this
        Paper.init(this)
        setupApplication()
        initRoleSettingsDefault()
        Mapbox.getInstance(
            applicationContext,
            "pk.eyJ1IjoiaHBodWM1NDQzIiwiYSI6ImNrcGZsd2s2ZDIyeXcyd2xsdjA3b3BsM3kifQ.KoieAuM1rHDdCOt122aFeQ"
        )
//        FirebaseMessaginxg.getInstance().isAutoInitEnabled = true
        HttpRequestUtil.setOkHttpClient(HttpClient.get())
    }

    private fun initRoleSettingsDefault() {
        val list = mutableListOf<SettingsRoleData>()

        // disaster
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.DISASTER.value,
                type = AppConstants.ROLE_POLICY,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.DISASTER.value,
                type = AppConstants.ROLE_CIVIL_DEFENSE,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.DISASTER.value,
                type = AppConstants.ROLE_STREET_BODYGUARD,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.DISASTER.value,
                type = AppConstants.ROLE_HOSPITAL,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.DISASTER.value,
                type = AppConstants.ROLE_FIRE_FIGHT,
                isChoose = false
            )
        )

        // crime
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.CRIME.value,
                type = AppConstants.ROLE_POLICY,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.CRIME.value,
                type = AppConstants.ROLE_CIVIL_DEFENSE,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.CRIME.value,
                type = AppConstants.ROLE_STREET_BODYGUARD,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.CRIME.value,
                type = AppConstants.ROLE_FIRE_FIGHT,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.CRIME.value,
                type = AppConstants.ROLE_HOSPITAL,
                isChoose = false
            )
        )

        // vehicle
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.VEHICLE.value,
                type = AppConstants.ROLE_POLICY,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.VEHICLE.value,
                type = AppConstants.ROLE_CIVIL_DEFENSE,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.VEHICLE.value,
                type = AppConstants.ROLE_STREET_BODYGUARD,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.VEHICLE.value,
                type = AppConstants.ROLE_FIRE_FIGHT,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.VEHICLE.value,
                type = AppConstants.ROLE_HOSPITAL,
                isChoose = false
            )
        )

        // accident
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.ACCIDENT.value,
                type = AppConstants.ROLE_POLICY,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.ACCIDENT.value,
                type = AppConstants.ROLE_CIVIL_DEFENSE,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.ACCIDENT.value,
                type = AppConstants.ROLE_STREET_BODYGUARD,
                isChoose = true
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.ACCIDENT.value,
                type = AppConstants.ROLE_FIRE_FIGHT,
                isChoose = false
            )
        )
        list.add(
            SettingsRoleData(
                accidentType = AccidentType.ACCIDENT.value,
                type = AppConstants.ROLE_HOSPITAL,
                isChoose = false
            )
        )
        ConfigUtil.saveListRoleSettings(list)
    }

    private fun setupApplication() {
        AppConfigs.getInstanceApp().setBaseApplication(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
        MultiDex.install(this) // this is the key code
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        LocaleManager.setLocale(this)
        super.onConfigurationChanged(newConfig)
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        @JvmStatic
        var app: App? = null
            private set

    }

}