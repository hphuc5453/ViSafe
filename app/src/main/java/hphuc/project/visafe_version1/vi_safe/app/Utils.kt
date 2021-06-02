package hphuc.project.visafe_version1.vi_safe.app

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import kotlinx.android.synthetic.main.view_dialog_error.view.*
import java.util.regex.Pattern

class Utils {
    companion object{
        @JvmStatic
        fun checkValidPhoneNumber(phoneNumberChecked: String): Boolean {
            if (AppConstants.dontCheckHeadPhoneNumber) {
                return true
            }
            val startIndex = if (phoneNumberChecked.startsWith("0")) {
                1
            } else {
                0
            }
            val endIndex = startIndex + 2
            val headPhoneNumber = phoneNumberChecked.substring(startIndex, endIndex)
            return AppConstants.LIST_HEAD_PHONE_NUMBER.contains(headPhoneNumber) && Pattern.matches("0+[1-9]+[0-9]{8}", phoneNumberChecked)
        }

        @JvmStatic
        fun showErrorTextDialog(
            mvpActivity: MvpActivity,
            content: String,
            onActionNotify: OnActionNotify? = null,
            isCancelable: Boolean = true
        ) {
            val layout =
                LayoutInflater.from(mvpActivity).inflate(R.layout.view_dialog_error, null, false)
            val dialogError =
                AlertDialog.Builder(mvpActivity, R.style.DialogNotify).setView(layout).create()
            layout.tvContent.text = content
            dialogError.setCancelable(isCancelable)
            layout.flClose.setOnClickListener {
                onActionNotify?.onActionNotify()
                dialogError?.dismiss()
            }
            dialogError.show()
        }

        @JvmStatic
        fun getMyLocation(context: Context): Location? {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return null
                }
                val gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                return gpsLocation ?: if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                } else null
            } catch (e: java.lang.Exception) {
                return null
            }
        }
    }
}