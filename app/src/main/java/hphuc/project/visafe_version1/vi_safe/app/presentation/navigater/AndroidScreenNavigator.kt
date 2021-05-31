package hphuc.project.visafe_version1.vi_safe.app.presentation.navigater

import android.content.Intent
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity

import hphuc.project.visafe_version1.vi_safe.app.presentation.navigation.ScreenNavigator
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity

class AndroidScreenNavigator constructor(private val mvpActivity: MvpActivity) : ScreenNavigator {
    override fun gotoMainActivity() {
        val intent = Intent(mvpActivity, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        mvpActivity.startActivity(intent)
    }


}