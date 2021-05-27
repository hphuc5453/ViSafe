package hphuc.project.visafe_version1.core.base.utils

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.net.ConnectivityManager
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.mapbox.mapboxsdk.geometry.LatLng
import hphuc.project.visafe_version1.App
import hphuc.project.visafe_version1.core.app.common.AppConfigs
import kotlinex.boolean.getValueOrDefault
import java.text.DecimalFormat
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class Util {
    class Resource {
        companion object {
            @JvmStatic
            fun getString(@StringRes idStr: Int): String =
                    AppConfigs.instance.getBaseApplication().getString(idStr)

            @JvmStatic
            fun getIntResource(@IntegerRes intRes: Int): Int {
                return AppConfigs.instance.getBaseApplication().resources.getInteger(intRes)
            }

            @JvmStatic
            fun getColor(colorId: Int): Int =
                    ContextCompat.getColor(AppConfigs.instance.getBaseApplication(), colorId)

            @JvmStatic
            fun getDrawable(@DrawableRes drawableId: Int): Drawable {
                return ContextCompat.getDrawable(AppConfigs.instance.getBaseApplication(), drawableId)!!
            }

            @JvmStatic
            fun changeDrawableColor(background: Drawable, @ColorInt colorToSet: Int) {
                when (background) {
                    is ShapeDrawable -> {
                        // cast to 'ShapeDrawable'
                        val shapeDrawable = background.mutate() as ShapeDrawable
                        shapeDrawable.paint.color = colorToSet
                    }
                    is GradientDrawable -> {
                        // cast to 'GradientDrawable'
                        val gradientDrawable = background.mutate() as GradientDrawable
                        gradientDrawable.setColor(colorToSet)
                    }
                    is ColorDrawable -> {
                        // alpha value may need to be set again after this call
                        val colorDrawable = background.mutate() as ColorDrawable
                        colorDrawable.color = colorToSet
                    }
                }
            }


            @JvmStatic
            fun getIntDimen(@DimenRes id: Int): Int {
                return AppConfigs.instance.getBaseApplication().resources.getDimension(id).toInt()
            }
        }
    }

    class Func {
        companion object {
            @JvmStatic
            fun isNetworkAvailable(context: Context): Boolean {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo?.isConnected.getValueOrDefault()
            }

            @JvmStatic
            fun isNetworkConnected(): Boolean {
                val cm = App.app?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            }

            @JvmStatic
            fun isAppOnForeground(context: Context): Boolean {
                var isAppOnForeground = false
                val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val runningProcesses =
                        am.runningAppProcesses
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND&&processInfo.processName==context.packageName) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.packageName) {
                                isAppOnForeground = true
                            }
                        }
                    }
                }

                return isAppOnForeground
            }

            @JvmStatic
            fun calculationByDistance(StartP: LatLng, EndP: LatLng): Double {
                val radius = 6371// radius of earth in Km
                val lat1 = StartP.latitude
                val lat2 = EndP.latitude
                val lon1 = StartP.longitude
                val lon2 = EndP.longitude
                val dLat = Math.toRadians(lat2 - lat1)
                val dLon = Math.toRadians(lon2 - lon1)
                val a = sin(dLat / 2) * sin(dLat / 2) + (cos(Math.toRadians(lat1))
                        * cos(Math.toRadians(lat2)) * sin(dLon / 2)
                        * sin(dLon / 2))
                val c = 2 * asin(sqrt(a))

                val valueResult = radius * c
                val km = valueResult / 1
                val newFormat = DecimalFormat("####")
                val kmInDec = Integer.valueOf(newFormat.format(km))
                val meter = valueResult % 1000
                val meterInDec = Integer.valueOf(newFormat.format(meter))
//                Log.i(
//                    "Radius Value", "" + valueResult + "   KM  " + kmInDec
//                            + " Meter   " + meterInDec
//                )

                return radius * c
            }
        }
    }

}