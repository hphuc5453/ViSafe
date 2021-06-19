package hphuc.project.visafe_version1.vi_safe.app

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import kotlinex.view.gone
import kotlinex.view.visible
import kotlinx.android.synthetic.main.view_dialog_error.view.*
import java.util.regex.Pattern

class Utils {
    companion object {
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
            return AppConstants.LIST_HEAD_PHONE_NUMBER.contains(headPhoneNumber) && Pattern.matches(
                "0+[1-9]+[0-9]{8}",
                phoneNumberChecked
            )
        }

        @JvmStatic
        fun showErrorTextDialog(
            mvpActivity: MvpActivity,
            content: String? = null,
            onActionNotify: OnActionNotify? = null,
            isCancelable: Boolean = true,
            isShowAccept: Boolean = false,
            onActionAccept: OnActionNotify? = null,
        ) {
            val layout =
                LayoutInflater.from(mvpActivity).inflate(R.layout.view_dialog_error, null, false)
            val dialogError =
                AlertDialog.Builder(mvpActivity, R.style.DialogNotify).setView(layout).create()
            layout.tvContent.text = content
            dialogError.setCancelable(isCancelable)
            if (isShowAccept) {
                layout.flAccept.visible()
            } else {
                layout.flAccept.gone()
            }
            layout.flClose.setOnClickListener {
                onActionNotify?.onActionNotify()
                dialogError?.dismiss()
            }
            layout.flAccept.setOnClickListener {
                onActionAccept?.onActionNotify()
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

        @JvmStatic
        fun setPaddingStatusBar(view: View, context: Context) {
            view.setPadding(0, getStatusBarHeight(context), 0, 0)
        }

        @JvmStatic
        fun setPaddingNavigationBar(view: View, context: Context) {
            view.setPadding(0, 0, 0, getNavigationBarHeight(context))
        }

        @JvmStatic
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        @JvmStatic
        fun getNavigationBarHeight(context: Context): Int {
            var result = 0
            val resourceId =
                context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        @JvmStatic
        fun convertDpToPx(dpInput: Float): Float {
            return (dpInput * Resources.getSystem().displayMetrics.density)
        }


        @SuppressLint("InflateParams")
        fun makeText(
            context: Context,
            message: String? = null,
            duration: Int = Toast.LENGTH_LONG,
        ): Toast {
            val toast = Toast(context)
            val layout =
                LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null, false)
            val tvMessage = layout.findViewById<AppCompatTextView>(R.id.tvMessage)

            layout.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            if (!message.isNullOrEmpty()) {
                tvMessage.text = message
            }
            toast.view = layout
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.duration = duration
            return toast
        }

        @SuppressLint("InflateParams")
        fun makeText(context: Context, image: Drawable?, duration: Int = Toast.LENGTH_LONG): Toast {
            val toast = Toast(context)
            val layout =
                LayoutInflater.from(context)
                    .inflate(R.layout.layout_custom_toast_with_image, null, false)

            val ivView = layout.findViewById<AppCompatImageView>(R.id.ivNotify)
            ivView.setImageDrawable(image)
            layout.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            toast.view = layout
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = duration
            return toast
        }
    }
}