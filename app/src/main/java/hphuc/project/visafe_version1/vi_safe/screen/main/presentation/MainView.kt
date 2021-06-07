package hphuc.project.visafe_version1.vi_safe.screen.main.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.ViewResult
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.home.HomeFragment
import hphuc.project.visafe_version1.vi_safe.screen.home_map.HomeMapFragment
import hphuc.project.visafe_version1.vi_safe.screen.home_map.data.HomeMapDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.ListContactsFragment
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.data.ListContactsDataIntent
import kotlinex.mvpactivity.showErrorAlert
import kotlinex.view.gone
import kotlinex.view.visible
import kotlinx.android.synthetic.main.layout_main.view.*

class MainView(mvpActivity: MvpActivity, viewCreator: AndroidMvpView.ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), MainContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_main, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mResource = MainResourceProvider(mvpActivity)
    private val mPresenter = MainPresenter(mvpActivity, mResource)

    private var homeFragment: HomeFragment? = null
    private var contactsFragment: ListContactsFragment? = null

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {
            when (data) {
                is HomeMapDataIntent -> {
                    mvpActivity.replaceFragment(HomeMapFragment.newInstance(data), view.flChange.id)
                }
                is ListContactsDataIntent -> {
                    showFragmentForMenuItem(NAVIGATION.LIST_CONTACT.value)
                }
            }
        }
    })

    companion object {
        const val REQUEST_GPS_MANAGER = 113
    }

    override fun onViewResult(viewResult: ViewResult) {
        super.onViewResult(viewResult)
        if (viewResult.resultCode == Activity.RESULT_OK) {
            mvpActivity.supportFragmentManager.fragments.forEach {
                it.onActivityResult(viewResult.requestCode, viewResult.resultCode, viewResult.data)
            }
        }
    }

    private fun showFragmentForMenuItem(itemId: Int) {
        view.clContainerSearch.gone()
        Handler(Looper.getMainLooper()).post {
            try {
                val ft = mvpActivity.supportFragmentManager.beginTransaction()
                checkFragmentExist()
                when (itemId) {
                    NAVIGATION.HOME.value -> if (homeFragment != null && homeFragment?.isAdded!!) {
                        view.clContainerSearch.visible()
                        ft.show(homeFragment!!)
                    } else {
                        homeFragment = HomeFragment()
                        view.clContainerSearch.visible()
                        ft.replace(R.id.flChange, homeFragment!!, itemId.toString())
                    }
                    NAVIGATION.LIST_CONTACT.value -> if (contactsFragment != null && contactsFragment?.isAdded!!) {
                        ft.show(contactsFragment!!)
                    } else {
                        contactsFragment = ListContactsFragment.newInstance()
                        ft.replace(R.id.flChange, contactsFragment!!, itemId.toString())
                    }
                }
                hideOtherFragment(ft, itemId)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ft.commit()

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun checkFragmentExist() {
        val fragments = mvpActivity.supportFragmentManager.fragments
        for (f in fragments) {
            if (homeFragment == null && f is HomeFragment) {
                homeFragment = f
            }
            if (contactsFragment == null && f is ListContactsFragment) {
                contactsFragment = f
            }
        }
    }

    enum class NAVIGATION(val value: Int) {
        HOME(R.id.actionHome), LIST_CONTACT(R.id.actionFriend)
    }

    private fun hideOtherFragment(ft: FragmentTransaction, itemId: Int) {
        if (homeFragment != null && homeFragment!!.isAdded && itemId != NAVIGATION.HOME.value)
            ft.hide(homeFragment!!)
        if (contactsFragment != null && contactsFragment!!.isAdded && itemId != NAVIGATION.LIST_CONTACT.value)
            ft.hide(contactsFragment!!)
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
        view.bottomNavigationBar.setOnNavigationItemSelectedListener {
            showFragmentForMenuItem(it.itemId)
            true
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