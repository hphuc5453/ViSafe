package hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.domain.excecutor.EventFireUtil
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.SettingsRoleResourceProvider
import hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.model.SettingsRoleItemViewModel
import kotlinx.android.synthetic.main.item_layout_settings_role_item_vholder.view.*

class SettingsRoleItemViewRenderer(
    context: Context,
    private val mResource: SettingsRoleResourceProvider,
    private val onChangeChoose: OnActionData<SettingsRoleItemViewModel>
) : ViewRenderer<SettingsRoleItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_settings_role_item_vholder
    }

    override fun getModelClass(): Class<SettingsRoleItemViewModel> =
        SettingsRoleItemViewModel::class.java

    override fun bindView(model: SettingsRoleItemViewModel, viewRoot: View) {
        viewRoot.tvRole.text = model.name
        when (model.type) {
            AppConstants.ROLE_POLICY -> {
                viewRoot.ivRole.setImageDrawable(mResource.getIconRolePolicy())
            }
            AppConstants.ROLE_CIVIL_DEFENSE -> {
                viewRoot.ivRole.setImageDrawable(mResource.getIconRoleCivilDefense())
            }
            else -> {
                viewRoot.ivRole.setImageDrawable(mResource.getIconRoleOther())
            }
        }
        viewRoot.ivChecked.setImageDrawable(if (model.isChoose) mResource.getIconChecked() else mResource.getIconUnCheck())
        viewRoot.ivChecked.setOnClickListener {
            model.isChoose = !model.isChoose
            EventFireUtil.fireEvent(onChangeChoose, model)
        }
    }

}