package hphuc.project.visafe_version1.vi_safe.screen.camera.presentation

import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.provider.AndroidResourceProvider
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

class CameraResourceProvider(mvpActivity: MvpActivity): AndroidResourceProvider(mvpActivity) {
    fun getTextRequirePermission(): String{
        return resourceManager.getString(R.string.text_error_write_perm_required)
    }
}