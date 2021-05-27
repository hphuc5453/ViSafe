package kotlinex.view

import android.graphics.Paint
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSize


inline fun <T : View> T.afterMeasured(crossinline f: T.(ViewSize) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                val viewSize = ViewSize(measuredWidth, measuredHeight)
                f(viewSize)
            }
        }
    })
}


inline fun <T : View> T.afterMeasuredSize(crossinline f: T.(ViewSize) -> Unit) {
    this.afterMeasured { viewSize ->
        f(viewSize)
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.isGone(): Boolean {
    return this.visibility == View.GONE
}

fun View.isInvisible(): Boolean {
    return this.visibility == View.INVISIBLE
}

fun TextView.strikeThrough() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.notStrikeThrough() {
    this.paintFlags = this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun TextView.notUnderLineThrough() {
    this.paintFlags = this.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
}

fun TextView.underLineThrough() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}
