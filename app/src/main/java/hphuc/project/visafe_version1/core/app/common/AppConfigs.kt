package hphuc.project.visafe_version1.core.app.common

import android.annotation.SuppressLint
import android.app.Application

class AppConfigs {
    private lateinit var baseApplication: Application

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var instance: AppConfigs = AppConfigs()

        @JvmStatic
        fun getInstanceApp(): AppConfigs {
            return instance
        }
    }

    fun setBaseApplication(baseApplication: Application) {
        this.baseApplication = baseApplication
    }

    fun getBaseApplication(): Application {
        return this.baseApplication
    }

}