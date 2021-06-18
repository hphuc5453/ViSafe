package hphuc.project.visafe_version1.vi_safe.screen.notify.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.notify.presentation.model.NotifyItemViewModel
import kotlinx.android.synthetic.main.item_layout_notify_vholder.view.*

class NotifyItemViewRenderer (context: Context): ViewRenderer<NotifyItemViewModel>(context){
    override fun getLayoutId(): Int {
        return R.layout.item_layout_notify_vholder
    }

    override fun getModelClass(): Class<NotifyItemViewModel> = NotifyItemViewModel::class.java

    override fun bindView(model: NotifyItemViewModel, viewRoot: View) {
        viewRoot.tvNotify.text = model.content
    }
}