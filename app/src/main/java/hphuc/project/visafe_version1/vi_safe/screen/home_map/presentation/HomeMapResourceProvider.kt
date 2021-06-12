package hphuc.project.visafe_version1.vi_safe.screen.home_map.presentation

import android.graphics.drawable.Drawable
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class HomeMapResourceProvider(mvpActivity: MvpActivity) : AndroidResourceProvider(mvpActivity){
    fun getTextTotalSupport(total: Int): String{
        return String.format(resourceManager.getString(R.string.text_total_person_supprort), total)
    }

    fun getIconChecked(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_check_blue)
    }

    fun getImageNotifyUrgent(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_notify_sent_urgent)
    }

    fun getIconSosDefault(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_sos_default)
    }

    fun getIconSosActive(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_sos_default)
    }
}