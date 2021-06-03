package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.home.HomeFragment
import hphuc.project.visafe_version1.vi_safe.screen.home_map.HomeMapFragment
import hphuc.project.visafe_version1.vi_safe.screen.home_map.data.HomeMapDataIntent
import kotlinex.mvpactivity.showErrorAlert
import kotlinx.android.synthetic.main.layout_main.view.*

class MainView(mvpActivity: MvpActivity, viewCreator: AndroidMvpView.ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), MainContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_main, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mResource = MainResourceProvider(mvpActivity)
    private val mPresenter = MainPresenter(mvpActivity, mResource)

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {
            when(data){
                is HomeMapDataIntent->{
                    mvpActivity.replaceFragment(HomeMapFragment(), view.flChange.id)
                }
            }
        }
    })

    companion object {
        const val REQUEST_GPS_MANAGER = 113
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        mvpActivity.setActivityFullScreen()
        view.ivArrow.setOnClickListener {
            if (view.eplSearch.isExpanded) {
                view.eplSearch.isExpanded = false
                view.ivArrow.setImageDrawable(mResource.getIconArrowUp())
            } else {
                view.eplSearch.isExpanded = true
                view.ivArrow.setImageDrawable(mResource.getIconArrowDown())
            }
        }
    }

    override fun handleGetMap() {
        mvpActivity.replaceFragment(HomeFragment(), view.flChange.id)
    }

    override fun startMvpView() {
        mPresenter.attachView(this)
        super.startMvpView()
    }

    override fun initData() {
        super.initData()
        mPresenter.registerAppPermission()
    }

    override fun handlePermissionFail() {
        showError(mResource.getTextRequirePermission())
    }

    override fun stopMvpView() {
        mPresenter.detachView()
        super.stopMvpView()
    }

    override fun showLoading() {
        loadingView.show()
    }

    override fun hideLoading() {
        loadingView.hide()
    }

    override fun showToast(message: String) {
        Toast.makeText(mvpActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(content: String) {
        mvpActivity.showErrorAlert(content)
    }

    @SuppressLint("ShowToast")
    override fun showRequestPermission(permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(
                mvpActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                mvpActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                mvpActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && ContextCompat.checkSelfPermission(
                mvpActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Snackbar.make(
                view, R.string.msg_need_permission,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(
                android.R.string.ok
            ) {
                ActivityCompat.requestPermissions(
                    mvpActivity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQUEST_GPS_MANAGER
                )
            }
        } else {
            ActivityCompat.requestPermissions(
                mvpActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_GPS_MANAGER
            )
        }
        return false
    }

    override fun handleAfterRequestPermission() {
        val manager = mvpActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mPresenter.checkLocation(manager)
        mPresenter.downloadStyleMap()
    }

    override fun showAlertMessageNoGps() {
        val builder = AlertDialog.Builder(mvpActivity)
        builder.setMessage(R.string.mgs_open_location)
            .setCancelable(false)
            .setPositiveButton(R.string.ACTION_WANT) { _, _ ->
                mvpActivity.startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    REQUEST_GPS_MANAGER
                )
            }
            .setNegativeButton(R.string.ACTION_NO) { dialog, _ ->
                dialog.cancel()
                mvpActivity.finish()
            }
        val alert = builder.create()
        alert.show()
    }
}