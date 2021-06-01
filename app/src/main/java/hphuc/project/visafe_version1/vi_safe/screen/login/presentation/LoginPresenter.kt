package hphuc.project.visafe_version1.vi_safe.screen.login.presentation

import hphuc.project.visafe_version1.vi_safe.app.data.network.request.LoginRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.PassportRequest
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator

class LoginPresenter(
    private val androidScreenNavigator: AndroidScreenNavigator,
) :
    LoginContact.Presenter() {

    override fun logInApp(request: LoginRequest) {

    }

    override fun logout() {

    }

    override fun login(request: PassportRequest) {
        androidScreenNavigator.gotoMainActivity()
    }

    override fun gotoSignUpActivity() {
        androidScreenNavigator.gotoSignUpActivity()
    }
}