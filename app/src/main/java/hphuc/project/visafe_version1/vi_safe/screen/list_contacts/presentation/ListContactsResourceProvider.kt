package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation

import android.graphics.drawable.Drawable
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class ListContactsResourceProvider(mvpActivity: MvpActivity): AndroidResourceProvider(mvpActivity) {
    fun getTextTitle(): String{
        return resourceManager.getString(R.string.text_contacts_list)
    }

    fun getIconRemove(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_remove_white)
    }

    fun getIconCall(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_call_white)
    }

    fun getIconMessage(): Drawable? {
        return resourceManager.getDrawable(R.drawable.ic_message_white)
    }

    fun getTextAcceptContact(): String{
        return resourceManager.getString(R.string.text_warning_accept_contact)
    }

}