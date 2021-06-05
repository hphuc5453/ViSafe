package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation

import android.Manifest
import android.os.Handler
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.domain.ListContactsMapper
import kotlinex.mvpactivity.shouldShowCheckPermission

class ListContactsPresenter(private val mvpActivity: MvpActivity, private val screenNavigator: AndroidScreenNavigator) : ListContactsContract.Presenter() {
    override fun checkPermission() {
        Handler().postDelayed({
            val onActionNotPermission = object : OnActionNotify {
                override fun onActionNotify() {
                    view?.showToast(mvpActivity.resources.getString(R.string.text_error_write_perm_required))
                }
            }
            val list = mutableListOf<String>()
            list.add(Manifest.permission.READ_CONTACTS)
            list.add(Manifest.permission.READ_CONTACTS)
            mvpActivity.shouldShowCheckPermission(
                list,
                object : OnActionNotify {
                    override fun onActionNotify() {
                        view?.handleCheckPermission()
                    }
                },
                onActionNotPermission,
                onActionNotPermission
            )
        }, 500)

    }

    override fun getData() {
        view?.handleInitData(ListContactsMapper().map(""))
    }

    override fun gotoContactsDetailActivity(extra: ContractsDetailDataIntent) {
        screenNavigator.gotoContactsDetailActivity(extra)
    }
}