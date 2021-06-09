package hphuc.project.visafe_version1.vi_safe.screen.home_map.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.app.util.image.GlideImageHelper
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.model.ListContactsItemViewModel
import kotlinx.android.synthetic.main.item_layout_home_map_support_vholder.view.*

class HomeMapSupportItemViewRenderer(context: Context) :
    ViewRenderer<ListContactsItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_home_map_support_vholder
    }

    override fun getModelClass(): Class<ListContactsItemViewModel> = ListContactsItemViewModel::class.java

    override fun bindView(model: ListContactsItemViewModel, viewRoot: View) {
        GlideImageHelper(context).loadAvatar(viewRoot.ivRole, model.avatar)
        viewRoot.tvRole.text = model.name
    }
}