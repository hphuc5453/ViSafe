package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail

import android.os.Bundle
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation.ContactsDetailView

class ContactsDetailActivity : MvpActivity() {
    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        val extra =
            this.intent?.getParcelableExtra<ContractsDetailDataIntent>(ContractsDetailDataIntent::class.java.simpleName)
        return ContactsDetailView(this, ContactsDetailView.ViewCreator(this, null), extra)
    }

    override fun getBackgroundScreen(): Int {
        return R.drawable.background
    }
}