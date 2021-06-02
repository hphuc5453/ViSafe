package hphuc.project.visafe_version1.core.base.presentation.mvp.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.LifeCycleAndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.PermissionsResult
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.ViewResult
import hphuc.project.visafe_version1.core.base.presentation.mvp.base.lifecycle.LifeCycleDispatcherSetter

abstract class MvpFragment : Fragment(), LifeCycleDispatcherSetter<LifeCycleAndroidMvpView> {
    private var mContext: Context? = null
    private var mRootView: View? = null

    private var androidMvpView: LifeCycleAndroidMvpView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun addLifeCycle(lifeCycle: LifeCycleAndroidMvpView) {
    }

    fun getMvpActivity(): MvpActivity {
        return activity as MvpActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        androidMvpView = createAndroidMvpView(savedInstanceState)
        mRootView = (androidMvpView as AndroidMvpView).createView()
        initMvpView()
        initViewAfterCreateView(savedInstanceState)
        return mRootView
    }

    protected open fun initViewAfterCreateView(savedInstanceState: Bundle?) {}
    override fun onStart() {
        super.onStart()
        androidMvpView?.startMvpView()
    }

    override fun onResume() {
        super.onResume()
        androidMvpView?.startMvpView()
    }

    override fun onPause() {
        super.onPause()
        androidMvpView?.stopMvpView()
    }

    private fun initMvpView() {
        androidMvpView?.initMvpView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        androidMvpView?.onViewResult(ViewResult(requestCode, resultCode, data))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        androidMvpView?.onRequestPermissionsResult(
            PermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        )
    }

    abstract fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView

}