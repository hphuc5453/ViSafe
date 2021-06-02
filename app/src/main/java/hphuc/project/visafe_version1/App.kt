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
import io.paperdb.Paper
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.network.HttpClient

class App : Application() {
    var currentActivity: MvpActivity?=null
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        app = this
        Paper.init(this)
        setupApplication()
//        initLogger()
//        initLogActivity()
        Mapbox.getInstance(applicationContext, "pk.eyJ1IjoiaHBodWM1NDQzIiwiYSI6ImNrcGZsd2s2ZDIyeXcyd2xsdjA3b3BsM3kifQ.KoieAuM1rHDdCOt122aFeQ")
//        Fresco.initialize(this)
//        FirebaseMessaginxg.getInstance().isAutoInitEnabled = true
        HttpRequestUtil.setOkHttpClient(HttpClient.get())
    }

//    private fun initLogger() {
//        Logger.addLogAdapter(object : AndroidLogAdapter() {
//            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return BuildConfig.DEBUG
//            }
//        })
//    }

//    private fun initLogActivity() {
//        if (BuildConfig.DEBUG) {
//            this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
//                override fun onActivityPaused(activity: Activity?) {
//                    activity?.let {
//                        Logger.d("onActivityPaused ${activity.javaClass.simpleName}")
//                    }
//                }
//
//                override fun onActivityResumed(activity: Activity?) {
//                    if (activity is MvpActivity) {
//                        currentActivity = activity
//                    }
//                    activity?.let {
//                        Logger.d("onActivityResumed ${activity.javaClass.simpleName}")
//                    }
//                }
//
//                override fun onActivityStarted(activity: Activity?) {
//                    activity?.let {
//                        Logger.d("onActivityStarted ${activity.javaClass.simpleName}")
//                    }
//                }
//
//                override fun onActivityDestroyed(activity: Activity?) {
//                    activity?.let {
//                        Logger.d("onActivityDestroyed ${activity.javaClass.simpleName}")
//                    }
//                }
//
//                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
//                    activity?.let {
//                        Logger.d("onActivitySaveInstanceState ${activity.javaClass.simpleName}")
//                    }
//                }
//
//                override fun onActivityStopped(activity: Activity?) {
//                    activity?.let {
//                        Logger.d("onActivityStopped ${activity.javaClass.simpleName}")
//                    }
//                }
//
//                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
//                    activity?.let {
//                        Logger.d("onActivityCreated ${activity.javaClass.simpleName}")
//                    }
//                }
//
//            })
//        }
//    }

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