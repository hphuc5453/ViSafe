package hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle

import hphuc.project.visafe_version1.core.base.presentation.mvp.base.lifecycle.LifeCycleMvpView

interface LifeCycleAndroidMvpView : LifeCycleMvpView {
    fun onViewResult(viewResult: ViewResult)
    fun onRequestPermissionsResult(permissionsResult: PermissionsResult)
}