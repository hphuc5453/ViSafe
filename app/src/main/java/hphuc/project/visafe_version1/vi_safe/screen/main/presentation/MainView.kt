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
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.lifecycle.ViewResult
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.home.HomeFragment
import hphuc.project.visafe_version1.vi_safe.screen.home.data.HomeDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.home_map.AccidentType
import hphuc.project.visafe_version1.vi_safe.screen.home_map.HomeMapFragment
import hphuc.project.visafe_version1.vi_safe.screen.home_map.data.HomeMapDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.ListContactsFragment
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.data.ListContactsDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.notify.NotifyFragment
import hphuc.project.visafe_version1.vi_safe.screen.notify.data.NotifyDataBusIntent
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
                    view.ivMenuAccident.visible()
                    setViewMenuAccident(data.accidentType)
                }
                is ListContactsDataIntent -> {
                    showFragmentForMenuItem(NAVIGATION.LIST_CONTACT.value)
                }
                is HomeDataIntent -> {
                    showFragmentForMenuItem(NAVIGATION.HOME.value)
                }
                is NotifyDataBusIntent-> {
                    view.clContainerSearch.gone()
                    mvpActivity.replaceFragment(NotifyFragment(), view.flChange.id)
                }
            }
        }
    })

    private var typeChoose : Int = AccidentType.QUICKLY.value
    private fun setViewMenuAccident(type: Int){
        var tyleSentDrawble = mResource.getIconNotifyQuickly()
        when(type){
            AccidentType.ACCIDENT.value->{
                view.ivMenuAccident.setImageDrawable(mResource.getIconMenuAccidentMini())

                view.ivMenuAccidentAccident.setImageDrawable(mResource.getIconMenuAccidentActive())
                view.ivMenuAccidentCrime.setImageDrawable(mResource.getIconMenuCrimeDefault())
                view.ivMenuAccidentDisaster.setImageDrawable(mResource.getIconMenuDisasterDefault())
                view.ivMenuAccidentVehicle.setImageDrawable(mResource.getIconMenuVehicleDefault())

                tyleSentDrawble = mResource.getIconNotifyAccident()
            }
            AccidentType.DISASTER.value->{
                view.ivMenuAccident.setImageDrawable(mResource.getIconMenuDisasterMini())

                view.ivMenuAccidentAccident.setImageDrawable(mResource.getIconMenuAccidentDefault())
                view.ivMenuAccidentCrime.setImageDrawable(mResource.getIconMenuCrimeDefault())
                view.ivMenuAccidentDisaster.setImageDrawable(mResource.getIconMenuDisasterActive())
                view.ivMenuAccidentVehicle.setImageDrawable(mResource.getIconMenuVehicleDefault())

                tyleSentDrawble = mResource.getIconNotifyDisaster()
            }
            AccidentType.CRIME.value->{
                view.ivMenuAccident.setImageDrawable(mResource.getIconMenuCrimeMini())

                view.ivMenuAccidentAccident.setImageDrawable(mResource.getIconMenuAccidentDefault())
                view.ivMenuAccidentCrime.setImageDrawable(mResource.getIconMenuCrimeActive())
                view.ivMenuAccidentDisaster.setImageDrawable(mResource.getIconMenuDisasterDefault())
                view.ivMenuAccidentVehicle.setImageDrawable(mResource.getIconMenuVehicleDefault())

                tyleSentDrawble = mResource.getIconNotifyCrime()
            }
            AccidentType.VEHICLE.value->{
                view.ivMenuAccident.setImageDrawable(mResource.getIconMenuVehicleMini())

                view.ivMenuAccidentAccident.setImageDrawable(mResource.getIconMenuAccidentDefault())
                view.ivMenuAccidentCrime.setImageDrawable(mResource.getIconMenuCrimeDefault())
                view.ivMenuAccidentDisaster.setImageDrawable(mResource.getIconMenuDisasterDefault())
                view.ivMenuAccidentVehicle.setImageDrawable(mResource.getIconMenuVehicleActive())

                tyleSentDrawble = mResource.getIconNotifyVehicle()
            }
            else->{
                view.ivMenuAccident.setImageDrawable(mResource.getIconMenuAccident())

                view.ivMenuAccidentAccident.setImageDrawable(mResource.getIconMenuAccidentDefault())
                view.ivMenuAccidentCrime.setImageDrawable(mResource.getIconMenuCrimeDefault())
                view.ivMenuAccidentDisaster.setImageDrawable(mResource.getIconMenuDisasterDefault())
                view.ivMenuAccidentVehicle.setImageDrawable(mResource.getIconMenuVehicleDefault())
            }
        }

        if (typeChoose != type){
            Utils.makeText(mvpActivity, tyleSentDrawble).show()
        }
        typeChoose = type
        view.clMenuAccident.gone()
    }

    private val onActionClick = View.OnClickListener {
        when(it.id){
            view.ivMenuAccident.id->{
               if (view.clMenuAccident.isVisible){
                   view.clMenuAccident.gone()
               }else{
                   view.clMenuAccident.visible()
               }
            }
            view.ivArrow.id->{
                if (view.eplSearch.isExpanded) {
                    view.eplSearch.isExpanded = false
                    view.ivArrow.setImageDrawable(mResource.getIconArrowUp())
                } else {
                    view.eplSearch.isExpanded = true
                    view.ivArrow.setImageDrawable(mResource.getIconArrowDown())
                }
            }
            view.ivMenuAccidentAccident.id->{
                setViewMenuAccident(AccidentType.ACCIDENT.value)
            }
            view.ivMenuAccidentCrime.id->{
                setViewMenuAccident(AccidentType.CRIME.value)
            }
            view.ivMenuAccidentDisaster.id->{
                setViewMenuAccident(AccidentType.DISASTER.value)
            }
            view.ivMenuAccidentVehicle.id->{
                setViewMenuAccident(AccidentType.VEHICLE.value)
            }
        }
    }
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
        view.ivMenuAccident.gone()
        typeChoose = AccidentType.QUICKLY.value
        Handler(Looper.getMainLooper()).post {
            try {
                val ft = mvpActivity.supportFragmentManager.beginTransaction()
                checkFragmentExist()
                when (itemId) {
                    NAVIGATION.HOME.value -> if (homeFragment != null && homeFragment?.isAdded!!) {
                        ft.show(homeFragment!!)
                    } else {
                        homeFragment = HomeFragment()
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
                if (itemId == NAVIGATION.HOME.value){
                    view.clContainerSearch.visible()
                }else{
                    view.clContainerSearch.gone()
                }

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

    @SuppressLint("ClickableViewAccessibility")
    private fun initView(){
        view.clMenuAccident.setOnTouchListener { _, _ ->
            true
        }
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        mvpActivity.setActivityFullScreen()
        initView()
        view.bottomNavigationBar.setOnNavigationItemSelectedListener {
            showFragmentForMenuItem(it.itemId)
            true
        }
        view.ivMenuAccident.setOnClickListener(onActionClick)
        view.ivMenuAccidentAccident.setOnClickListener(onActionClick)
        view.ivMenuAccidentDisaster.setOnClickListener(onActionClick)
        view.ivMenuAccidentCrime.setOnClickListener(onActionClick)
        view.ivMenuAccidentVehicle.setOnClickListener(onActionClick)
        view.ivArrow.setOnClickListener(onActionClick)
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