package hphuc.project.visafe_version1.vi_safe.screen.camera.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.ViewResult
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import kotlinex.mvpactivity.showErrorAlert
import kotlinx.android.synthetic.main.layout_camera.view.*
import kotlinx.android.synthetic.main.layout_choose_camera_or_video.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class CameraView(mvpActivity: MvpActivity, viewCreator: ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), CameraContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        LayoutViewCreator(R.layout.layout_camera, context, viewGroup)

    private val mPresenter = CameraPresenter(AndroidScreenNavigator(mvpActivity))
    @SuppressLint("InflateParams")
    private val alertView = LayoutInflater.from(mvpActivity).inflate(R.layout.layout_choose_camera_or_video, null, false)
    private val alertDialog = AlertDialog.Builder(mvpActivity, R.style.DialogNotify).setView(alertView).create()

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {

        }
    })

    private val onActionClick = View.OnClickListener {
        when(it.id){
            alertView.ivCamera.id->{
                mPresenter.gotoCameraActivity()
                alertDialog.dismiss()
            }
            alertView.ivVideo.id->{
                alertDialog.dismiss()
            }
            view.ivBack.id->{
                mvpActivity.onBackPressed()
            }
        }
    }

    override fun onViewResult(viewResult: ViewResult) {
        super.onViewResult(viewResult)
        if (viewResult.resultCode == Activity.RESULT_OK){
            when(viewResult.requestCode){
                AppConstants.REQUEST_CODE_PICK_IMAGE->{
                    val bitmap = viewResult.data?.extras?.get("data") as Bitmap
                    view.ivResultCamera.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun initAlertDialog(){
        alertView.ivCamera.setOnClickListener(onActionClick)
        alertView.ivVideo.setOnClickListener(onActionClick)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun initView(){
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
        view.ivBack.setOnClickListener(onActionClick)
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        initAlertDialog()
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