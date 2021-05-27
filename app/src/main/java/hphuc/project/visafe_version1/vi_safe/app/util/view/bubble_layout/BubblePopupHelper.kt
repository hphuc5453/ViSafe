package hphuc.project.visafe_version1.vi_safe.app.util.view.bubble_layout

import android.content.Context
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import hphuc.project.visafe_version1.R

object BubblePopupHelper {
    fun create(context: Context, bubbleLayout: BubbleLayout): PopupWindow {
        val popupWindow = PopupWindow(context)
        popupWindow.contentView = bubbleLayout
        popupWindow.isOutsideTouchable = true
        popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // change background color to transparent
        popupWindow.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.popup_window_transparent
            )
        )
        return popupWindow
    }
}
