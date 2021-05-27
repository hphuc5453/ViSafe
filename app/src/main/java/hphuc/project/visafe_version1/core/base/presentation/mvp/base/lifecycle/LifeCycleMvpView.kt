package hphuc.project.visafe_version1.core.base.presentation.mvp.base.lifecycle

import android.view.MotionEvent

interface LifeCycleMvpView {
    fun initMvpView()
    fun startMvpView()
    fun initData()
    fun stopMvpView()
    fun dispatchTouchEvent(ev: MotionEvent)
    fun isHandleBackPressed(): Boolean
}