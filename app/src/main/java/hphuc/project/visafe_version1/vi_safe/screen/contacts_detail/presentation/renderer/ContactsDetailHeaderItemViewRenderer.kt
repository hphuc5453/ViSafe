package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.domain.excecutor.EventFireUtil
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.app.util.image.GlideImageHelper
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation.model.ContactsDetailHeaderItemViewModel
import kotlinex.view.gone
import kotlinex.view.visible
import kotlinx.android.synthetic.main.item_layout_contacts_detail_header_vholder.view.*

class ContactsDetailHeaderItemViewRenderer(
    context: Context,
    private val onActionAdd: OnActionData<ContactsDetailHeaderItemViewModel>
) : ViewRenderer<ContactsDetailHeaderItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_contacts_detail_header_vholder
    }

    override fun getModelClass(): Class<ContactsDetailHeaderItemViewModel> =
        ContactsDetailHeaderItemViewModel::class.java

    override fun bindView(model: ContactsDetailHeaderItemViewModel, viewRoot: View) {
        GlideImageHelper(context).loadAvatar(viewRoot.ivAvatar, model.avatar)
        viewRoot.tvName.text = model.name
        viewRoot.tvPhone.text = model.phoneNumber
        if (model.isAdded) {
            viewRoot.ivAdd.gone()
        } else {
            viewRoot.ivAdd.visible()
        }
        viewRoot.ivAdd.setOnClickListener {
            EventFireUtil.fireEvent(onActionAdd, model)
        }
    }
}