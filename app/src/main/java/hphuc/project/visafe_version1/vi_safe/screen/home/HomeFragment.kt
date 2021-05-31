package hphuc.project.visafe_version1.vi_safe.screen.home

import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.home.presentation.HomeView

class HomeFragment : MvpFragment(){
    override fun createAndroidMvpView(): AndroidMvpView {
        return HomeView(getMvpActivity(), HomeView.ViewCreator(getMvpActivity(), null))
    }
}