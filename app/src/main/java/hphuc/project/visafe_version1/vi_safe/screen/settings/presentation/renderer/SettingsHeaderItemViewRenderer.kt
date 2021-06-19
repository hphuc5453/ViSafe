package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.app.util.image.GlideImageHelper
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsHeaderItemViewModel
import kotlinx.android.synthetic.main.item_layout_settings_header_vholder.view.*

class SettingsHeaderItemViewRenderer(context: Context) :
    ViewRenderer<SettingsHeaderItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_settings_header_vholder
    }

    override fun getModelClass(): Class<SettingsHeaderItemViewModel> =
        SettingsHeaderItemViewModel::class.java

    override fun bindView(model: SettingsHeaderItemViewModel, viewRoot: View) {
        viewRoot.tvName.text = model.name
        viewRoot.tvPhone.text = model.phoneNumber
        GlideImageHelper(context).loadAvatar(viewRoot.ivAvatar, model.avatar)
    }
}