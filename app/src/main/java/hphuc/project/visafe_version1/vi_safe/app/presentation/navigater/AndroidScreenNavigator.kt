package hphuc.project.visafe_version1.vi_safe.app.presentation.navigater

import android.content.Intent
import android.provider.ContactsContract
import android.provider.MediaStore
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants

import hphuc.project.visafe_version1.vi_safe.app.presentation.navigation.ScreenNavigator
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.ContactsDetailActivity
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.login.LoginActivity
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.SignUpActivity

class AndroidScreenNavigator constructor(private val mvpActivity: MvpActivity) : ScreenNavigator {
    override fun gotoMainActivity() {
        val intent = Intent(mvpActivity, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        mvpActivity.startActivity(intent)
    }

    override fun gotoSignUpActivity() {
        val intent = Intent(mvpActivity, SignUpActivity::class.java)
        mvpActivity.startActivity(intent)
    }

    override fun gotoLoginActivity() {
        val intent = Intent(mvpActivity, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        mvpActivity.startActivity(intent)
    }

    override fun gotoContactIntent() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        mvpActivity.startActivityForResult(intent, AppConstants.REQUEST_CODE_PICK_CONTRACT)
    }

    override fun gotoContactsDetailActivity(extra: ContractsDetailDataIntent) {
        val intent = Intent(mvpActivity, ContactsDetailActivity::class.java)
        intent.putExtra(ContractsDetailDataIntent::class.java.simpleName, extra)
        mvpActivity.startActivity(intent)
    }

    override fun gotoCameraActivity() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        mvpActivity.startActivityForResult(intent, AppConstants.REQUEST_CODE_PICK_IMAGE)
    }
}