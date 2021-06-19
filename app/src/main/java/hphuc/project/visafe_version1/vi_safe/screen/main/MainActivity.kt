package hphuc.project.visafe_version1.vi_safe.screen.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.screen.main.presentation.MainView

class MainActivity : MvpActivity() {

    companion object{

        @SuppressLint("StaticFieldLeak")
        lateinit var view: MainView
        var isLoading= false

        fun showLoading(){
            view.showLoading()
        }

        fun hideLoading(){
            view.hideLoading()
        }
    }

    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        view =  MainView(this, MainView.ViewCreator(this, null))
        return view
    }

    override fun getBackgroundScreen(): Int {
        return R.drawable.background
    }
}