@file:Suppress("DEPRECATION")

package hphuc.project.visafe_version1.core.app.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hphuc.project.visafe_version1.core.app.view.BaseDividerItemDecoration


class SimpleDividerItemDecoration(var context: Context, @DrawableRes private var drawableId: Int) : BaseDividerItemDecoration() {
    private var mDivider: Drawable = context.resources.getDrawable(drawableId)
    private var mShowFirstDivider: Boolean = false
    private var mShowLastDivider: Boolean = false

    override fun reset() {

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        val orientation = getOrientation(parent)
        val childCount = parent.childCount
        var size = 0
        if( orientation == LinearLayoutManager.VERTICAL){
            size = mDivider.intrinsicHeight
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
        } else if(orientation == LinearLayoutManager.HORIZONTAL){
            size = mDivider.intrinsicWidth
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
        }
        val from: Int = if (mShowFirstDivider) 0 else 1
        val to: Int = childCount -1

        for (i in from..to) {
            val child: View = parent.getChildAt(i)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            if(orientation == LinearLayoutManager.VERTICAL) {
                top = child.top - params.topMargin
                bottom = top + size
            } else if(orientation == LinearLayoutManager.HORIZONTAL ){
                left = child.left - params.leftMargin
                right = left + size
            }
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
        if (mShowLastDivider && childCount > 0){
            val child: View = parent.getChildAt(childCount - 1)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            if(orientation == LinearLayoutManager.VERTICAL){
                top = child.bottom + params.bottomMargin
                bottom = top + size
            } else if(orientation == LinearLayoutManager.HORIZONTAL){
                left = child.right + params.rightMargin
                right = left + size
            }
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }

    }

    private fun getOrientation(parent: RecyclerView): Int {
        if (parent.layoutManager is LinearLayoutManager) {
            val layoutManager: LinearLayoutManager = parent.layoutManager as LinearLayoutManager
            return layoutManager.orientation
        } else {
            throw IllegalStateException("DividerItemDecoration can only be used with a LinearLayoutManager.")
        }

    }
}