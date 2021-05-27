package hphuc.project.visafe_version1.core.base.domain.manager

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import kotlinex.number.getValueOrDefaultIsZero
import hphuc.project.visafe_version1.core.app.common.AppConfigs
import hphuc.project.visafe_version1.core.base.manager.LocaleManager
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil


class AndroidResourceManager : ResourceManager {

    override fun getString(@StringRes stringId: Int): String {
        return if ((ConfigUtil.passport?.languageId.getValueOrDefaultIsZero() == 1
                    && LocaleManager.getLocale(AppConfigs.instance.getBaseApplication().resources!!) == LocaleManager.VI) ||
            (ConfigUtil.passport?.languageId.getValueOrDefaultIsZero() == 2
                    && LocaleManager.getLocale(AppConfigs.instance.getBaseApplication().resources!!) == LocaleManager.ENGLISH)) {
            AppConfigs.instance.getBaseApplication().resources.getString(stringId)
        } else if (ConfigUtil.passport != null) {
            LocaleManager.setNewLocale(
                AppConfigs.instance.getBaseApplication(),
                if (ConfigUtil.passport?.languageId.getValueOrDefaultIsZero() == 1) LocaleManager.VI else LocaleManager.ENGLISH)
            AppConfigs.instance.getBaseApplication().resources.getString(stringId)
        } else {
            AppConfigs.instance.getBaseApplication().getString(stringId)
        }
    }

    override fun getQuantityString(stringId: Int, total: Int): String =
        AppConfigs.instance.getBaseApplication().resources.getQuantityString(stringId, total)

    override fun getInt(@IntegerRes intId: Int): Int = AppConfigs.instance.getBaseApplication().resources?.getInteger(intId)!!

    override fun getDrawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(
        AppConfigs.instance.getBaseApplication(), drawableId)

    @ColorInt
    override fun getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(AppConfigs.instance.getBaseApplication(), colorId)

    override fun pxToDp(px: Int): Int = (px.toFloat() / Resources.getSystem().displayMetrics.density).toInt()

    override fun dpToPx(dp: Int): Int = (dp.toFloat() * Resources.getSystem().displayMetrics.density).toInt()

    override fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int {
        return AppConfigs.instance.getBaseApplication().resources?.getDimensionPixelSize(dimenRes)!!
    }
}