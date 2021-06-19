package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.domain.excecutor.EventFireUtil
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsPremiumItemViewModel
import kotlinx.android.synthetic.main.item_layout_settings_premium_vholder.view.*

class SettingsPremiumItemViewRenderer(
    context: Context,
    private val onActionPremium: OnActionNotify
) : ViewRenderer<SettingsPremiumItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_settings_premium_vholder
    }

    override fun getModelClass(): Class<SettingsPremiumItemViewModel> =
        SettingsPremiumItemViewModel::class.java

    override fun bindView(model: SettingsPremiumItemViewModel, viewRoot: View) {
        viewRoot.ivPremium.setOnClickListener {
            EventFireUtil.fireEvent(onActionPremium)
        }
    }
}