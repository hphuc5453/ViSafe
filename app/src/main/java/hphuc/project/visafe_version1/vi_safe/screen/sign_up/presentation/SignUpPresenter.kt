package hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation

import hphuc.project.visafe_version1.vi_safe.screen.sign_up.domain.SignUpMapper

class SignUpPresenter(private val mResource: SignUpResourceProvider) : SignUpContract.Presenter(){
    override fun getData() {
        view?.showData(SignUpMapper(mResource).map(""))
    }

    override fun signUp() {
        view?.handleSignUp()
    }
}