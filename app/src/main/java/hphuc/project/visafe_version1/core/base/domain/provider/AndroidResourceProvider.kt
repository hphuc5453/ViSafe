package hphuc.project.visafe_version1.core.base.domain.provider

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.manager.AndroidResourceManager
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

abstract class AndroidResourceProvider(val mvpActivity: MvpActivity) : ResourceProvider(
    AndroidResourceManager()
){

    fun getColorWhite():Int{
        return resourceManager.getColor(R.color.white)
    }

    fun getColorBlack():Int{
        return resourceManager.getColor(R.color.black)
    }

    fun getTypeFaceMedium() : Typeface?{
        return ResourcesCompat.getFont(mvpActivity, R.font.semi_regular)
    }

    fun getTypeFaceBold() : Typeface?{
        return ResourcesCompat.getFont(mvpActivity, R.font.semi_bold)
    }

    fun getTypeFaceRegular() : Typeface?{
        return ResourcesCompat.getFont(mvpActivity, R.font.semi_regular)
    }

    fun getTypeFaceItalic() : Typeface?{
        return ResourcesCompat.getFont(mvpActivity, R.font.semi_italic)
    }

    fun getIconShowPassword(): Int{
        return R.drawable.ic_orion_show_password
    }

    fun getIconHidePassword(): Int{
        return R.drawable.ic_orion_hidden_password
    }

    fun getIconRolePolicy(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_role_policy)
    }

    fun getIconRoleOther(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_role_other)
    }

    fun getIconRoleCivilDefense(): Drawable?{
        return resourceManager.getDrawable(R.drawable.ic_role_civi_defense)
    }

    fun getTextRolePolicy(): String{
        return resourceManager.getString(R.string.text_role_policy)
    }

    fun getTextRoleCivilDefense(): String{
        return resourceManager.getString(R.string.text_role_civil_defense)
    }

    fun getTextRoleStreetBodyguard(): String{
        return resourceManager.getString(R.string.text_role_street_bodyguard)
    }

    fun getTextRoleHospital(): String{
        return resourceManager.getString(R.string.text_role_hospital)
    }

    fun getTextRoleFirefight(): String{
        return resourceManager.getString(R.string.text_role_fire_fight)
    }
}
