package hphuc.project.visafe_version1.core.base.presentation.mvp.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.domain.manager.AndroidResourceManager
import hphuc.project.visafe_version1.core.base.manager.LocaleManager
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidDispatcher
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.PermissionsResult
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.ViewResult
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.lifecycle.LifeCycleDispatcherSetter

abstract class MvpActivity : AppCompatActivity(),
    LifeCycleDispatcherSetter<LifeCycleAndroidMvpView> {
    private val lifeCycleDispatcher = LifeCycleAndroidDispatcher()
    private var timeBackPressed: Long = 0
    private val timeDelayWhenClickBack = 2000
    //    private val resultSyncOrderOfflineReceiver = ResultSyncOrderOfflineReceiver()
    private lateinit var screenReceiver: ScreenReceiver
    private val resource = AndroidResourceManager()

    override fun addLifeCycle(lifeCycle: LifeCycleAndroidMvpView) {
        lifeCycleDispatcher.addLifeCycle(lifeCycle)
    }

    fun replaceFragment(f: Fragment, flChange: Int) {
        this.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(flChange, f)
            .commit()
    }

    fun setActivityFullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resource.getColor(R.color.color_status_bar_opacity)
            window.navigationBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
        injectIntent()
        initScreenOnEvent()
        initCreateView(savedInstanceState)
        initMvpView()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

    private fun initWindow() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val drawableId = getBackgroundScreen()
        if(drawableId!=0) {
            setBackgroundData(drawableId)
        }
    }

    private fun setBackgroundData(@DrawableRes id: Int) {
        val resource = AndroidResourceManager()
        val background = resource.getDrawable(id)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resource.getColor(android.R.color.transparent)
        window.navigationBarColor = resource.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }

    @DrawableRes
    open fun getBackgroundScreen(): Int {
        return R.drawable.background
    }

    override fun onDestroy() {
        removeScreenOnEvent()
        super.onDestroy()
    }

    private fun initScreenOnEvent() {
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        screenReceiver = ScreenReceiver(object : OnActionNotify {
            override fun onActionNotify() {
                onScreenOn()
            }
        }, object : OnActionNotify {
            override fun onActionNotify() {
                onScreenOff()
            }
        })
        registerReceiver(screenReceiver, filter)
    }

    private fun removeScreenOnEvent() {
        unregisterReceiver(screenReceiver)
    }

    protected open fun onScreenOn() {
        Logger.d("onScreenOn")
    }


    protected open fun onScreenOff() {
        Logger.d("onScreenOff")
    }


    private fun initBroadcastReceiverAfterSyncOrderOffline() {
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(ResultSyncOrderOfflineReceiver.EXTRA_ACTION_GET_RESULT_SYNC_ORDER)
//        registerReceiver(resultSyncOrderOfflineReceiver, intentFilter)
    }

    abstract fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView

    @CallSuper
    protected open fun initCreateView(savedInstanceState: Bundle?) {
        val androidMvpView = createAndroidMvpView(savedInstanceState)
        setupContentView(androidMvpView)
    }

    private fun setupContentView(setupMetaView: AndroidMvpView) {
        setupMetaView.let {
            val view = it.createView()
            setContentView(view)
            addLifeCycle(it)
        }
    }

    private fun initMvpView() {
        lifeCycleDispatcher.initMvpView()
    }

    override fun onResume() {
        super.onResume()
        lifeCycleDispatcher.startMvpView()
        initBroadcastReceiverAfterSyncOrderOffline()
    }


    override fun onPause() {
        super.onPause()
        lifeCycleDispatcher.stopMvpView()
//        unregisterReceiver(resultSyncOrderOfflineReceiver)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        lifeCycleDispatcher.dispatchTouchEvent(ev)
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifeCycleDispatcher.onViewResult(ViewResult(requestCode, resultCode, data))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        lifeCycleDispatcher.onRequestPermissionsResult(
            PermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        )
    }

    private fun injectIntent() {
//        ActivityIntentInjector().injectIntent(this)
    }

    inline fun <reified T : Parcelable> getInjectIntentExtra(): T? {
        return if (intent != null) {
            intent?.extras?.getParcelable(T::class.java.simpleName)
        } else {
            null
        }
    }

    override fun onBackPressed() {
        if (!handleBackPressed()) {
            super.onBackPressed()
        }

    }

    private fun handleBackPressed(): Boolean {
        var isHandleBack = lifeCycleDispatcher.isHandleBackPressed()
        if (!isHandleBack) {
            if (this.isTaskRoot) {
                if (timeBackPressed + timeDelayWhenClickBack > System.currentTimeMillis()) {
                    isHandleBack = false
                } else {
                    isHandleBack = true
                    Toast.makeText(this, R.string.check_double_click_exit_app, Toast.LENGTH_SHORT)
                        .show()
                }
                timeBackPressed = System.currentTimeMillis()
            }
        }
        return isHandleBack
    }

    class ScreenReceiver(
        private val onScreenOn: OnActionNotify,
        private val onScreenOff: OnActionNotify
    ) :
        BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_SCREEN_OFF) {
                onScreenOff.onActionNotify()
            } else if (intent.action == Intent.ACTION_SCREEN_ON) {
                onScreenOn.onActionNotify()
            }
        }
    }
}