package hphuc.project.visafe_version1.core.base.domain.provider

import android.graphics.Typeface
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
        return ResourcesCompat.getFont(mvpActivity, R.font.roboto_medium)
    }

    fun getTypeFaceRegular() : Typeface?{
        return ResourcesCompat.getFont(mvpActivity, R.font.roboto_regular)
    }

    fun getTypeFaceItalic() : Typeface?{
        return ResourcesCompat.getFont(mvpActivity, R.font.roboto_italic)
    }
}
