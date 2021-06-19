package hphuc.project.visafe_version1.vi_safe.screen.home

import android.os.Bundle
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.home.presentation.HomeView
import hphuc.project.visafe_version1.vi_safe.screen.home_map.HomeMapFragment

class HomeFragment : MvpFragment() {

    companion object{
        val TAG = HomeMapFragment::class.java.simpleName
    }

    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return HomeView(getMvpActivity(), HomeView.ViewCreator(getMvpActivity(), null))
    }
}