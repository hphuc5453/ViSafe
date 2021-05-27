package hphuc.project.visafe_version1.core.base.presentation.mvp.android

import android.content.Context
import android.view.*
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import hphuc.project.visafe_version1.core.app.util.DoubleTouchPrevent
import hphuc.project.visafe_version1.core.app.util.DoubleTouchPreventProvider
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidDispatcher
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.PermissionsResult
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.ViewResult
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.MvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.lifecycle.LifeCycleDispatcherSetter

abstract class AndroidMvpView(val mvpActivity: MvpActivity, viewCreator: ViewCreator) : MvpView,
    LifeCycleAndroidMvpView, LifeCycleDispatcherSetter<LifeCycleAndroidMvpView> {

    protected val view: View = viewCreator.createView()
    private var lifeCycleDispatcher: LifeCycleAndroidDispatcher = LifeCycleAndroidDispatcher()
    private val doubleTouchPrevent: DoubleTouchPrevent = DoubleTouchPreventProvider.createDoubleTouchPrevent()
    private var isFirstTimeInitData = true

    fun createView(): View {
        initCreateView()
        return view
    }

    protected abstract fun initCreateView()

    override fun addLifeCycle(lifeCycle: LifeCycleAndroidMvpView) {
        lifeCycleDispatcher.addLifeCycle(lifeCycle)
    }

    @CallSuper
    override fun initMvpView() {
        lifeCycleDispatcher.initMvpView()
    }

    override fun initData() {
    }

    @CallSuper
    override fun startMvpView() {
        lifeCycleDispatcher.startMvpView()
        if (isFirstTimeInitData) {
            isFirstTimeInitData = false
            initData()
        }
    }

    @CallSuper
    override fun dispatchTouchEvent(ev: MotionEvent) {
        lifeCycleDispatcher.dispatchTouchEvent(ev)
    }

    override fun isHandleBackPressed(): Boolean {
        return lifeCycleDispatcher.isHandleBackPressed()
    }

    @CallSuper
    override fun stopMvpView() {
        lifeCycleDispatcher.stopMvpView()
    }

    interface ViewCreator {
        fun createView(): View
    }

    class ViewRootViewCreator(var view: View) : ViewCreator {
        override fun createView(): View = view
    }

    open class LayoutViewCreator(@LayoutRes private val layoutId: Int, private val context: Context, private val viewGroup: ViewGroup?) :
        ViewCreator {
        override fun createView(): View =
                LayoutInflater.from(context).inflate(layoutId, viewGroup, false)
    }

    @CallSuper
    override fun onViewResult(viewResult: ViewResult) {
        lifeCycleDispatcher.onViewResult(viewResult)
    }

    @CallSuper
    override fun onRequestPermissionsResult(permissionsResult: PermissionsResult) {
        lifeCycleDispatcher.onRequestPermissionsResult(permissionsResult)
    }

    fun runWithCheckMultiTouch(actionKey: String, onCheckMultiTouchPassEvent: OnActionNotify) {
        if (doubleTouchPrevent.check(actionKey)) {
            onCheckMultiTouchPassEvent.onActionNotify()
        }
    }

    fun runWithCheckMultiTouch(actionKey: String, onCheckMultiTouchPassEvent: OnActionNotify, timePrevent: Int) {
        doubleTouchPrevent.updateTimePreventDoubleTouch(timePrevent)
        if (doubleTouchPrevent.check(actionKey)) {
            onCheckMultiTouchPassEvent.onActionNotify()
        }
    }
}