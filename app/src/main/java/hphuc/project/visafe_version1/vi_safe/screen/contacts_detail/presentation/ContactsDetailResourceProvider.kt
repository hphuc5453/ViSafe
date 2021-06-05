package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation

import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class ContactsDetailResourceProvider(mvpActivity: MvpActivity) : AndroidResourceProvider(mvpActivity){
    fun getTextTitle(): String{
        return resourceManager.getString(R.string.text_contacts_list)
    }

    fun getTextAcceptContact(): String{
        return resourceManager.getString(R.string.text_warning_accept_contact)
    }
}