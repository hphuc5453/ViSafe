package hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.SignUpResourceProvider
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.model.RoleItemViewModel
import kotlinex.view.gone
import kotlinex.view.visible
import kotlinx.android.synthetic.main.item_layout_role_vholder.view.*

class RoleItemViewRenderer(context: Context, private val mResource: SignUpResourceProvider) :
    ViewRenderer<RoleItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_role_vholder
    }

    override fun getModelClass(): Class<RoleItemViewModel> = RoleItemViewModel::class.java

    override fun bindView(model: RoleItemViewModel, viewRoot: View) {
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
        viewRoot.tvRole.text = model.name
        if (model.isChoose){
            viewRoot.ivRoleChecked.visible()
        }else{
            viewRoot.ivRoleChecked.gone()
        }
    }
}