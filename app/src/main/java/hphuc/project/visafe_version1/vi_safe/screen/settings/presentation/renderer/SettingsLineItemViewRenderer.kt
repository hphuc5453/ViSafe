package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsLineItemViewModel

class SettingsLineItemViewRenderer(context: Context) : ViewRenderer<SettingsLineItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_settings_line_vholder
    }

    override fun getModelClass(): Class<SettingsLineItemViewModel> = SettingsLineItemViewModel::class.java

    override fun bindView(model: SettingsLineItemViewModel, viewRoot: View) {
    }
}