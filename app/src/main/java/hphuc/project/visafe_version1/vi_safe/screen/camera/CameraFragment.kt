package hphuc.project.visafe_version1.vi_safe.screen.camera

import android.os.Bundle
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.camera.presentation.CameraView

class CameraFragment : MvpFragment() {
    companion object{
        val TAG = CameraFragment::class.java.simpleName
    }
    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return CameraView(getMvpActivity(), CameraView.ViewCreator(getMvpActivity(), null))
    }
}