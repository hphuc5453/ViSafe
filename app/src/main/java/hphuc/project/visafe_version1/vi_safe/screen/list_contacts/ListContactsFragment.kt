package hphuc.project.visafe_version1.vi_safe.screen.list_contacts

import android.os.Bundle
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpFragment
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.ListContactsView

class ListContactsFragment : MvpFragment() {

    companion object {
        val TAG = ListContactsFragment::class.java.simpleName
    }

    override fun createAndroidMvpView(savedInstanceState: Bundle?): AndroidMvpView {
        return ListContactsView(getMvpActivity(), ListContactsView.ViewCreator(getMvpActivity(), null))
    }
}