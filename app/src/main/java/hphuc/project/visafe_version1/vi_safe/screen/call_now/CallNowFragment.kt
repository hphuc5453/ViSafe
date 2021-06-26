package hphuc.project.visafe_version1.vi_safe.screen.call_now

import android.os.Bundle
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.call_now.presentation.CallNowView

class CallNowFragment : MvpFragment() {
    companion object {
        val TAG = CallNowFragment::class.java.simpleName
    }

    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return CallNowView(getMvpActivity(), CallNowView.ViewCreator(getMvpActivity(), null))
    }
}