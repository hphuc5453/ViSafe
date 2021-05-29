package hphuc.project.visafe_version1.vi_safe.screen.login.presentation

import com.apollographql.apollo.ApolloClient
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.LoginRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.PassportRequest
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator

class LoginPresenter(
    val mResource: LoginResourceProvider,
    private val androidScreenNavigator: AndroidScreenNavigator,
) :
    LoginContact.Presenter() {

    private val apolloClient = ApolloClient.builder().build()


    override fun logInApp(request: LoginRequest) {

    }

    override fun logout() {

    }

    override fun login(request: PassportRequest) {
//        apolloClient.query(LoginApp)
    }
}