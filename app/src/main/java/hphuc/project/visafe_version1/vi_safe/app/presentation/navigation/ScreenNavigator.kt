package hphuc.project.visafe_version1.vi_safe.app.presentation.navigation

import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent

interface ScreenNavigator {
    fun gotoMainActivity()
    fun gotoSignUpActivity()
    fun gotoLoginActivity()
    fun gotoContactsDetailActivity(extra: ContractsDetailDataIntent)
    fun gotoContactIntent()
    fun gotoCameraActivity()
}