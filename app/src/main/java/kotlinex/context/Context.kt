package kotlinex.context

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.PluralsRes
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify

fun Context.alert(msg: String, title: String): AlertDialog.Builder {
    return AlertDialog.Builder(this).setTitle(title).setMessage(msg)
}

fun Context.showAlert(msg: String, title: String, onActionPositive: OnActionNotify?, onActionNegative: OnActionNotify?, onActionNeutral: OnActionNotify?) {
    this.alert(msg, title)
            .setPositiveButton(getString(R.string.ACTION_RETRY)) { _, _ ->
                onActionPositive?.onActionNotify()
            }
            .setNegativeButton(getString(R.string.ACTION_CANCEL)) { _, _ ->
                onActionNegative?.onActionNotify()
            }
            .setNeutralButton("") { _, _ ->
                onActionNeutral?.onActionNotify()
            }
            .show().setCancelable(false)

}

fun Context.showAlert(msg: String, title: String, onActionPositive: OnActionNotify?, onActionNegative: OnActionNotify?) {
    this.alert(msg, title).setPositiveButton(getString(R.string.ACTION_YES)) { _, _ ->
        onActionPositive?.onActionNotify()
    }.setNegativeButton(getString(R.string.ACTION_CANCEL)) { _, _ ->
        onActionNegative?.onActionNotify()
    }.show().setCancelable(false)
}

fun Context.showAlert(msg: String, title: String, onActionPositive: OnActionNotify?) {
    this.alert(msg, title).setPositiveButton(getString(R.string.ACTION_OK)) { _, _ ->
        onActionPositive?.onActionNotify()
    }.show().setCancelable(false)
}

fun Context.showAlert(msg: String, title: String, positiveStr: String, negative: String, onActionPositive: OnActionNotify?, onActionNegative: OnActionNotify?) {
    this.alert(msg, title).setPositiveButton(positiveStr) { _, _ ->
        onActionPositive?.onActionNotify()
    }.setNegativeButton(negative) { _, _ ->
        onActionNegative?.onActionNotify()
    }.show().setCancelable(false)
}

fun Context.showAlert(msg: String, title: String, positiveStr: String, negative: String, neutral: String, onActionPositive: OnActionNotify?, onActionNegative: OnActionNotify?, onActionNeutral: OnActionNotify?) {
    this.alert(msg, title).setPositiveButton(positiveStr) { _, _ ->
        onActionPositive?.onActionNotify()
    }.setNegativeButton(negative) { _, _ ->
        onActionNegative?.onActionNotify()
    }.setNeutralButton(neutral) { _, _ ->
        onActionNeutral?.onActionNotify()
    }.show().setCancelable(false)
}

fun Context.getQuantityString(@PluralsRes idStr: Int, quantity: Int): String {
    return this.resources.getQuantityString(idStr, quantity)
}