package hphuc.project.visafe_version1.core.base.presentation.mvp.android.list

import android.view.View

interface OnItemRvLongClickedListener<in D>: OnItemRvClickedListener<D> {
    fun onItemLongClicked(view: View, position: Int, dataItem: D)
}