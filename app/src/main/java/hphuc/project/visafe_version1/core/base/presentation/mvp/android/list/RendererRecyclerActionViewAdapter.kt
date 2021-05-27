package hphuc.project.visafe_version1.core.base.presentation.mvp.android.list

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import hphuc.project.visafe_version1.core.base.presentation.view.ViewAction

class RendererRecyclerActionViewAdapter : RendererRecyclerViewAdapter() {
    var viewAction: ViewAction? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any?>?) {
        super.onBindViewHolder(holder, position, payloads)
        if (viewAction != null) {
            viewAction?.action(holder.itemView)
        }
    }
}