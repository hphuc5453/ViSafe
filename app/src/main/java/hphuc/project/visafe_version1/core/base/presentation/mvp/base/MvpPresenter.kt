package hphuc.project.visafe_version1.core.base.presentation.mvp.base

import java.lang.ref.WeakReference

abstract class MvpPresenter<V : MvpView> : BaseMvpPresenter<V> {
    //protected val screenNavigator: ScreenNavigator, protected val resourceManager: ResourceManager

    private var weakReference: WeakReference<V>? = null
    override fun attachView(view: V) {
        if (!isViewAttached) {
            weakReference = WeakReference(view)
        }
    }

    override fun detachView() {
        weakReference?.clear()
        weakReference = null
    }

    val view: V?
        get() = weakReference?.get()

    private val isViewAttached: Boolean
        get() = weakReference != null && weakReference!!.get() != null
}
