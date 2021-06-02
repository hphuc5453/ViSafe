package hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.ListViewMvp
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.OnItemRvClickedListener
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.model.RoleItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.renderer.RoleItemViewRenderer
import kotlinex.mvpactivity.showErrorAlert
import kotlinex.number.getValueOrDefaultIsZero
import kotlinx.android.synthetic.main.layout_sign_up.view.*
import kotlinx.android.synthetic.main.layout_sign_up.view.edtPassword
import kotlinx.android.synthetic.main.layout_sign_up.view.ivHidden

class SignUpView(mvpActivity: MvpActivity, viewCreator: AndroidMvpView.ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), SignUpContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_sign_up, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mResource = SignUpResourceProvider(mvpActivity)
    private val mPresenter = SignUpPresenter(mResource)

    private val listData = mutableListOf<ViewModel>()
    private val renderInput = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        orientation = LinearRenderConfigFactory.Orientation.HORIZONTAL
    )
    private val renderConfig = LinearRenderConfigFactory(renderInput).create()

    private var listViewMvp: ListViewMvp? = null

    private val onItemRvClickedListener = object : OnItemRvClickedListener<ViewModel>{
        override fun onItemClicked(view: View, position: Int, dataItem: ViewModel) {
            when(dataItem){
                is RoleItemViewModel->{
                    listData.forEach{
                        if (it is RoleItemViewModel){
                            it.isChoose = false
                        }
                    }
                    dataItem.isChoose = true
                }
            }
            listViewMvp?.notifyDataChanged()
        }
    }

    private var isHidePassword = true
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

    private val onActionClick = View.OnClickListener {
        when(it.id){
            view.ivHidden.id->{
                isHidePassword = !isHidePassword
                changeShowHidePass(view.ivHidden, view.edtPassword)
            }
            view.btnSignUp.id->{
                checkData()
            }
            view.tvSignIn.id->{
                mvpActivity.onBackPressed()
            }
        }
    }

    private fun checkData(){
        if (view.edtName.text.isNullOrEmpty()){
            showError(mResource.getTextPleaseEnterName())
            return
        }
        if (view.edtPhoneNumber.text.isNullOrEmpty()){
            showError(mResource.getTextPleaseEnterPhoneNumber())
            return
        }
        if (view.edtPhoneNumber.text.toString().length <= 4){
            showError(mResource.getTextPhoneIncorrect())
            return
        }
        if (!Utils.checkValidPhoneNumber(view.edtPhoneNumber.text.toString())){
            showError(mResource.getTextPhoneIncorrect())
            return
        }
        if (view.edtPassword.text.isNullOrEmpty()){
            showError(mResource.getTextErrorEmptyPass())
            return
        }
        mPresenter.signUp()
    }

    private fun initView(){
        view.ivHidden.setOnClickListener(onActionClick)
        view.btnSignUp.setOnClickListener(onActionClick)
        view.tvSignIn.setOnClickListener(onActionClick)
    }

    private fun initRecycleView() {
        listViewMvp = ListViewMvp(mvpActivity, view.rvRole, renderConfig)
        listViewMvp?.addViewRenderer(RoleItemViewRenderer(mvpActivity, mResource))
        listViewMvp?.setOnItemRvClickedListener(onItemRvClickedListener)
        listViewMvp?.createView()
    }

    override fun handleSignUp() {
        mvpActivity.onBackPressed()
    }

    override fun initCreateView() {
        initView()
        initRecycleView()
    }

    override fun initData() {
        super.initData()
        mPresenter.getData()
    }

    override fun showData(list: MutableList<ViewModel>) {
        listData.clear()
        listData.addAll(list)
        listViewMvp?.setItems(listData)
        listViewMvp?.notifyDataChanged()
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