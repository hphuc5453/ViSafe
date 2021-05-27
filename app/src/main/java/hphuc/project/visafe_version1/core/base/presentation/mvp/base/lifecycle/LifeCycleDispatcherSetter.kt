package hphuc.project.visafe_version1.core.base.presentation.mvp.base.lifecycle

interface LifeCycleDispatcherSetter<in T: LifeCycleMvpView> {
    fun addLifeCycle(lifeCycle: T)
}