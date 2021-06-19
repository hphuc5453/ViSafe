package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsMethodItemViewModel
import kotlinx.android.synthetic.main.item_layout_settings_method_vholder.view.*

class SettingsMethodItemViewRenderer(context: Context): ViewRenderer<SettingsMethodItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_settings_method_vholder
    }

    override fun getModelClass(): Class<SettingsMethodItemViewModel> = SettingsMethodItemViewModel::class.java
    override fun bindView(model: SettingsMethodItemViewModel, viewRoot: View) {
        viewRoot.ivIcon.setImageResource(model.icon)
        viewRoot.tvName.text = model.name
    }
}