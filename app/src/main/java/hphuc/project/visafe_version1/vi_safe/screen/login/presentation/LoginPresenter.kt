package hphuc.project.visafe_version1.vi_safe.screen.login.presentation

import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.LoginRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.PassportRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.PassportResponse
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator

class LoginPresenter(
    private val androidScreenNavigator: AndroidScreenNavigator,
) :
    LoginContact.Presenter() {

    override fun logInApp(request: LoginRequest) {
        if (ConfigUtil.listSupport.isNullOrEmpty()){
            ConfigUtil.saveListSupport(mutableListOf())
        }
        androidScreenNavigator.gotoMainActivity()
    }

    override fun logout() {

    }

    override fun login(request: PassportRequest) {
        ConfigUtil.saveIsFirstLoginApp(true)
        ConfigUtil.savePassport(PassportResponse(
            userId = 1,
            mobile = "0334326686",
            fullName = "Lê Hoàng Phúc"
        ))
        androidScreenNavigator.gotoMainActivity()
    }

    override fun gotoSignUpActivity() {
        androidScreenNavigator.gotoSignUpActivity()
    }
}