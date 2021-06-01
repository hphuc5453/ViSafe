package hphuc.project.visafe_version1.vi_safe.screen.login.presentation

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.data.network.request.PassportRequest
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.PassportResponse
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator
import kotlinex.mvpactivity.showErrorAlert
import kotlinex.number.getValueOrDefaultIsZero
import kotlinx.android.synthetic.main.layout_login.view.*


class LoginView(mvpActivity: MvpActivity, viewCreator: ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator),
    LoginContact.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_login, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mResource = LoginResourceProvider(mvpActivity)
    private val mPresenter = LoginPresenter(AndroidScreenNavigator(mvpActivity))

    private fun initView(){
        view.btnLogin.setOnClickListener(onActionClick)
        view.btnSignUp.setOnClickListener(onActionClick)
        view.ivHidden.setOnClickListener(onActionClick)
    }

    private fun changeShowHidePass(
        ivSeen: AppCompatImageView,
        edtText: AppCompatEditText
    ) {
        if (!isHidePassword) {
            mResource.getIconShowPassword().let { ivSeen.setImageResource(it) }
            edtText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            edtText.setSelection(edtText.text?.length.getValueOrDefaultIsZero())
        } else {
            mResource.getIconHidePassword().let { ivSeen.setImageResource(it) }
            edtText.transformationMethod = PasswordTransformationMethod.getInstance()
            edtText.setSelection(edtText.text?.length.getValueOrDefaultIsZero())
        }
    }

    private fun checkData(){
        if (view.edtPhoneNumber.text.isNullOrEmpty()) {
            showError(mResource.getTextPleaseEnterPhoneNumber())
            return
        }
        if (view.edtPassword.text.isNullOrEmpty()) {
            showError(mResource.getTextErrorEmptyPass())
            return
        }
        if (view.edtPhoneNumber.text?.length.getValueOrDefaultIsZero() <= 3){
            showError(mResource.getTextPhoneIncorrect())
            return
        }
        if (!Utils.checkValidPhoneNumber(view.edtPhoneNumber.text.toString())) {
            showError(mResource.getTextPhoneIncorrect())
            return
        }
        val request = PassportRequest(
            user = view.edtPhoneNumber.text.toString(),
            pass = view.edtPassword.text.toString()
        )
        mPresenter.login(request)
    }

    private var isHidePassword = true
    private val onActionClick = View.OnClickListener {
        when(it.id){
            view.btnLogin.id->{
                checkData()
            }
            view.btnSignUp.id->{
                mPresenter.gotoSignUpActivity()
            }
            view.ivHidden.id->{
                isHidePassword = !isHidePassword
                changeShowHidePass(view.ivHidden, view.edtPassword)
            }
        }
    }

    override fun handleLoginWithPassport(passport: PassportResponse) {
        ConfigUtil.saveIsFirstLoginApp(true)
        ConfigUtil.savePassport(passport)
    }

    override fun initCreateView() {
        initView()
    }

    override fun handleLogout() {
    }

    override fun startMvpView() {
        mPresenter.attachView(this)
        super.startMvpView()
    }

    override fun stopMvpView() {
        mPresenter.detachView()
        super.stopMvpView()
    }

    override fun showLoading() {
        loadingView.show()
    }

    override fun hideLoading() {
        loadingView.hide()
    }

    override fun showToast(message: String) {
        Toast.makeText(mvpActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(content: String) {
        mvpActivity.showErrorAlert(content)
    }
}