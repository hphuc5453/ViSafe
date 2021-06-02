package hphuc.project.visafe_version1.vi_safe.screen.home

import android.os.Bundle
import com.mapbox.mapboxsdk.maps.MapView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.home.presentation.HomeView

class HomeFragment : MvpFragment(){
    lateinit var view : HomeView
    lateinit var mapView: MapView
    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        view=  HomeView(getMvpActivity(), HomeView.ViewCreator(getMvpActivity(), null))
        mapView = view.getMapView()
        mapView.onCreate(savedInstanceState)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}