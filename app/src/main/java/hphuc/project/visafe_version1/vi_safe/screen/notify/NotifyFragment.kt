package hphuc.project.visafe_version1.vi_safe.screen.notify

import android.os.Bundle
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.notify.presentation.NotifyView

class NotifyFragment: MvpFragment() {
    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return NotifyView(getMvpActivity(), NotifyView.ViewCreator(getMvpActivity(), null))
    }
}