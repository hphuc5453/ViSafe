package hphuc.project.visafe_version1.vi_safe.screen.camera.presentation

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import kotlinex.mvpactivity.showErrorAlert
import kotlinx.android.synthetic.main.layout_camera.view.*

class CameraView(mvpActivity: MvpActivity, viewCreator: ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), CameraContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        LayoutViewCreator(R.layout.layout_camera, context, viewGroup)

    private val mPresenter = CameraPresenter()

    private fun initView(){
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
    }

    override fun initCreateView() {
        initView()
    }

    override fun startMvpView() {
        mPresenter.attachView(this)
        super.startMvpView()
    }

    override fun stopMvpView() {
        mPresenter.detachView()
        super.stopMvpView()
    }

    override fun showLoading() {
        MainActivity.showLoading()
    }

    override fun hideLoading() {
        MainActivity.hideLoading()
    }

    override fun showToast(message: String) {
        Toast.makeText(mvpActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(content: String) {
        mvpActivity.showErrorAlert(content)
    }
}