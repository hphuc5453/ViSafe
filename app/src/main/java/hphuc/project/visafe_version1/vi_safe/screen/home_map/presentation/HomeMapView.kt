package hphuc.project.visafe_version1.vi_safe.screen.home_map.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.ListViewMvp
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.home_map.AccidentType
import hphuc.project.visafe_version1.vi_safe.screen.home_map.data.HomeMapDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.home_map.presentation.renderer.HomeMapSupportItemViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import kotlinex.collection.getValueOrDefault
import kotlinex.mvpactivity.showErrorAlert
import kotlinex.string.getValueOrDefaultIsEmpty
import kotlinex.view.gone
import kotlinex.view.visible
import kotlinx.android.synthetic.main.layout_home_map.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlinx.android.synthetic.main.view_alert_dialog_choose_list_support.view.*


class HomeMapView(
    mvpActivity: MvpActivity,
    viewCreator: AndroidMvpView.ViewCreator,
    private val extra: HomeMapDataIntent?,
) :
    AndroidMvpView(mvpActivity, viewCreator), HomeMapContract.View, MapboxMap.OnMapClickListener {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_home_map, context, viewGroup)

    private val mPresenter = HomeMapPresenter()
    private val mResource = HomeMapResourceProvider(mvpActivity)

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {

        }
    })

    private var mapBoxMap: MapboxMap? = null
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var zoomLevel: Double = 14.0
    private var source: GeoJsonSource? = null
    private val FILL_SOURCE_ID = "SOURCE-ID"
    private val LINE_SOURCE_ID = "LINE_SOURCE_ID"
    private val FILL_LAYER_ID = "FILL-LAYER-ID"
    private val LINE_LAYER_ID = "LINE-LAYER-ID"
    private val INFO_PROJECT_LAYER = "INFO_PROJECT_LAYER"
    private var PROJECT_SOURCE_ID = "PROJECT_SOURCE_ID"
    private val GREY_COLOR = Color.parseColor("#246d30ab")
    private val BORDER_COLOR = Color.parseColor("#6d30ab")
    private val FILL_OPACITY = .9f
    private val LINE_WIDTH = 1f
    private var featureCollection: FeatureCollection? = null
    private var isSos = false

    @SuppressLint("InflateParams")
    private val alertView = LayoutInflater.from(mvpActivity)
        .inflate(R.layout.view_alert_dialog_choose_list_support, null, false)
    private val alertDialog = AlertDialog.Builder(mvpActivity, R.style.DialogNotify).setView(alertView).create()

    private var listViewMvp: ListViewMvp? = null
    private val listData: MutableList<ViewModel> = mutableListOf()
    private val renderInput = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        orientation = LinearRenderConfigFactory.Orientation.HORIZONTAL
    )
    private val renderConfig = LinearRenderConfigFactory(renderInput).create()

    private fun initAlertDialog() {
        alertView.tvYourFamily.text = mResource.getTextTotalSupport(ConfigUtil.listSupport.getValueOrDefault().size)
        alertView.tvCancel.setOnClickListener(onActionClick)
        alertView.btnAlarm.setOnClickListener(onActionClick)
        alertView.llYourFamily.setOnClickListener(onActionClick)
        alertView.llPolicy.setOnClickListener(onActionClick)
        alertView.llCivilDefense.setOnClickListener(onActionClick)
        alertView.llStreetBodyGuard.setOnClickListener(onActionClick)
        alertView.llHospital.setOnClickListener(onActionClick)
        alertView.llFireFight.setOnClickListener(onActionClick)

        if (ConfigUtil.listSupport.getValueOrDefault().isNotEmpty()){
            alertView.llYourFamily.visible()
            alertView.tvYourFamily.visible()
            listViewMvp = ListViewMvp(mvpActivity, alertView.rvListSupport, renderConfig)
            listViewMvp?.addViewRenderer(HomeMapSupportItemViewRenderer(mvpActivity))
            listViewMvp?.createView()

            listData.clear()
            listData.addAll(ConfigUtil.listSupport.getValueOrDefault())
            listViewMvp?.setItems(listData)
            listViewMvp?.notifyDataChanged()
        }else{
            alertView.llYourFamily.gone()
            alertView.tvYourFamily.gone()
        }
    }

    private val onActionClick = View.OnClickListener {
        when(it.id){
            alertView.tvCancel.id->{
                alertDialog.dismiss()
            }
            view.ivBack.id->{
                mvpActivity.onBackPressed()
            }
            alertView.btnAlarm.id->{
                Utils.makeText(mvpActivity, mResource.getImageNotifyUrgent()).show()
                alertDialog.dismiss()
            }
            alertView.llYourFamily.id->{
                if (alertView.ivCheckYourFamily.drawable == null){
                    alertView.ivCheckYourFamily.setImageDrawable(mResource.getIconChecked())
                }else{
                    alertView.ivCheckYourFamily.setImageDrawable(null)
                }
            }
            alertView.llPolicy.id->{
                if (alertView.ivCheckPolicy.drawable == null){
                    alertView.ivCheckPolicy.setImageDrawable(mResource.getIconChecked())
                }else{
                    alertView.ivCheckPolicy.setImageDrawable(null)
                }
            }
            alertView.llCivilDefense.id->{
                if (alertView.ivCheckCivilDefense.drawable == null){
                    alertView.ivCheckCivilDefense.setImageDrawable(mResource.getIconChecked())
                }else{
                    alertView.ivCheckCivilDefense.setImageDrawable(null)
                }
            }
            alertView.llStreetBodyGuard.id->{
                if (alertView.ivCheckStreetBodyGuard.drawable == null){
                    alertView.ivCheckStreetBodyGuard.setImageDrawable(mResource.getIconChecked())
                }else{
                    alertView.ivCheckStreetBodyGuard.setImageDrawable(null)
                }
            }
            alertView.llHospital.id->{
                if (alertView.ivCheckHospital.drawable == null){
                    alertView.ivCheckHospital.setImageDrawable(mResource.getIconChecked())
                }else{
                    alertView.ivCheckHospital.setImageDrawable(null)
                }
            }
            alertView.llFireFight.id->{
                if (alertView.ivFireFight.drawable == null){
                    alertView.ivFireFight.setImageDrawable(mResource.getIconChecked())
                }else{
                    alertView.ivFireFight.setImageDrawable(null)
                }
            }
        }
    }

    private fun initView() {
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
        when (extra?.accidentType) {
            AccidentType.QUICKLY.value -> {
                alertDialog.show()
            }
        }
        view.ivBack.setOnClickListener(onActionClick)
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        initAlertDialog()
        initView()
    }

    override fun getOnMapReadyCallback(): OnMapReadyCallback = OnMapReadyCallback { mapBox ->
        mapBoxMap = mapBox
        mapBoxMap?.addOnMapClickListener(this)
        mapBoxMap?.uiSettings?.apply {
//            isRotateGesturesEnabled = true
//            isRotateVelocityAnimationEnabled = false
            isLogoEnabled = false
            isAttributionEnabled = false
//            isTiltGesturesEnabled = false
            isCompassEnabled = false
        }
        mapBoxMap?.setStyle(
            Style.Builder().fromUri(ConfigUtil.mapStyle.getValueOrDefaultIsEmpty())
        ) {
            enableLocationComponent(it)
        }
    }

    private fun enableLocationComponent(@NonNull loadedMapStyle: Style) {
//     Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(mvpActivity)) {
            // Get an instance of the component
            val locationComponent = mapBoxMap?.locationComponent
            val customLocationComponentOptions = LocationComponentOptions.builder(mvpActivity)
                .foregroundDrawable(mResource.getIconYourLocation())
                .build()

            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(mvpActivity, loadedMapStyle)
                    .locationComponentOptions(customLocationComponentOptions)
                    .build()
            locationComponent?.activateLocationComponent(locationComponentActivationOptions)

            // Enable to make component visible
            if (ActivityCompat.checkSelfPermission(
                    mvpActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    mvpActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationComponent?.isLocationComponentEnabled = true

            // Set the component's camera mode
//            locationComponent?.cameraMode = CameraMode.TRACKING

            // Set the component's render mode
            locationComponent?.renderMode = RenderMode.COMPASS
        }
        animateCameraToYourLocation()
        loadMapView()
    }


    override fun loadMapView() {
        setUpSource()
    }

    private fun setUpSource() {
        if (mapBoxMap == null) {
            return
        }
        source = null
        mapBoxMap?.getStyle { style ->
            initProjectSource(style)
            initProjectLayer(style)
            initFillSource(style)
            initLineSource(style)
        }
    }

    private fun initProjectLayer(style: Style) {
        val layer = style.getLayer(INFO_PROJECT_LAYER)
        if (layer != null) {
            if (Property.NONE == layer.visibility.getValue()) {
                layer.setProperties(PropertyFactory.visibility(Property.VISIBLE))
            }
        } else {
            style.addLayer(
                SymbolLayer(INFO_PROJECT_LAYER, PROJECT_SOURCE_ID)
                    .withProperties(
                        PropertyFactory.iconImage("{id}"),
                        PropertyFactory.iconAllowOverlap(false),
                        PropertyFactory.iconIgnorePlacement(false)
                    )
            )
        }
    }

    private fun initLineSource(style: Style) {
        style.addSource(GeoJsonSource(LINE_SOURCE_ID, FeatureCollection.fromFeatures(arrayOf())))
        val lineLayer = LineLayer(LINE_LAYER_ID, LINE_SOURCE_ID)
        lineLayer.withProperties(
            PropertyFactory.lineColor(BORDER_COLOR),
            PropertyFactory.lineWidth(LINE_WIDTH),
            PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
            PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND)
        )
        style.addLayerBelow(lineLayer, INFO_PROJECT_LAYER)
    }

    private fun initFillSource(style: Style) {
        style.addSource(
            GeoJsonSource(
                FILL_SOURCE_ID,
                FeatureCollection.fromFeatures(arrayOf())
            )
        )
        style.addLayerBelow(
            FillLayer(FILL_LAYER_ID, FILL_SOURCE_ID).withProperties(
                PropertyFactory.fillColor(GREY_COLOR),
                PropertyFactory.fillOpacity(FILL_OPACITY)
            ), INFO_PROJECT_LAYER
        )
    }

    private fun initProjectSource(style: Style) {
        if (source == null) {
            source = GeoJsonSource(
                PROJECT_SOURCE_ID, featureCollection, GeoJsonOptions()
            )
            style.addSource(source!!)
        }
    }

    private fun animateCameraToYourLocation() {
        Utils.getMyLocation(mvpActivity)?.let {
            lat = it.latitude
            lng = it.longitude
            animateCamera()
        }
    }

    private fun animateCamera() {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(lat, lng))
            .zoom(zoomLevel)
            .build()
        mapBoxMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 100)
    }

    override fun initData() {
        super.initData()
        getMapView().getMapAsync(getOnMapReadyCallback())
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

    fun getMapView(): MapView {
        return view.mapView
    }

    override fun onMapClick(point: LatLng): Boolean {
        return true
    }

}