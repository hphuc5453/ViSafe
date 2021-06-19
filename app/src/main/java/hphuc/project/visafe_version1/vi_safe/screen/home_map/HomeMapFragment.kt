package hphuc.project.visafe_version1.vi_safe.screen.home_map

import android.os.Bundle
import com.mapbox.mapboxsdk.maps.MapView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.home_map.data.HomeMapDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.home_map.presentation.HomeMapView
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.ListContactsFragment

class HomeMapFragment : MvpFragment() {
    lateinit var view: HomeMapView
    lateinit var mapView: MapView

    companion object {

        val TAG = HomeMapFragment::class.java.simpleName

        fun newInstance(data: HomeMapDataIntent): HomeMapFragment {
            val f = HomeMapFragment()
            val bundle = Bundle()
            bundle.putParcelable(HomeMapDataIntent::class.java.simpleName, data)
            f.arguments = bundle
            return f
        }
    }

    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        val extra =
            this.arguments?.getParcelable<HomeMapDataIntent>(HomeMapDataIntent::class.java.simpleName)
        view = HomeMapView(getMvpActivity(), HomeMapView.ViewCreator(getMvpActivity(), null),
            extra)
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