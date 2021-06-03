package hphuc.project.visafe_version1.vi_safe.app.lifecycle

import android.view.MotionEvent
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.bus.EventBusHandler
import hphuc.project.visafe_version1.core.base.bus.EventBusLifeCycleManager
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.PermissionsResult
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.ViewResult

class EventBusLifeCycle constructor(private val onEventBusActionData: OnActionData<EventBusData>) : LifeCycleAndroidMvpView,
    EventBusHandler {
    override fun onReceiveEvent(data: EventBusData) {
        onEventBusActionData.onAction(data)
    }

    override fun onViewResult(viewResult: ViewResult) {
    }

    override fun onRequestPermissionsResult(permissionsResult: PermissionsResult) {
    }

    override fun initMvpView() {
    }

    override fun startMvpView() {
        EventBusLifeCycleManager.getInstance()?.register(this)
    }

    override fun initData() {
    }

    override fun stopMvpView() {
        EventBusLifeCycleManager.getInstance()?.unregister(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent) {
    }

    override fun isHandleBackPressed(): Boolean = false

    fun sendData(data:EventBusData){
        EventBusLifeCycleManager.getInstance()?.publishEvent(data)
    }
}