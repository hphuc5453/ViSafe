package hphuc.project.visafe_version1.vi_safe.screen.home.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.home_map.AccidentType
import hphuc.project.visafe_version1.vi_safe.screen.home_map.data.HomeMapDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.data.ListContactsDataIntent
import kotlinex.mvpactivity.showErrorAlert
import kotlinex.view.gone
import kotlinex.view.visible
import kotlinx.android.synthetic.main.layout_home.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class HomeView(mvpActivity: MvpActivity, viewCreator: ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), HomeContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_home, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mPresenter = HomePresenter()
    private val mResource = HomeResourceProvider(mvpActivity)

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {

        }
    })

    private val onAccept = object : OnActionNotify{
        override fun onActionNotify() {
            eventBusLifeCycle.sendData(ListContactsDataIntent())
        }

    }

    private val onActionClick = View.OnClickListener {
        when(it.id){
            view.ivSos.id->{
//                if (ConfigUtil.listSupport.isNullOrEmpty()){
//                    showError(mResource.getTextPleaseChoosePersonSupport(), onAccept)
//                }
                eventBusLifeCycle.sendData(HomeMapDataIntent(
                    AccidentType.QUICKLY.value
                ))
            }
        }
    }

    private fun initView(){
        view.ivSos.setOnClickListener(onActionClick)
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        initView()
        view.ivBack.gone()
        view.ivHistory.visible()
        view.clHistory.visible()
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
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

    override fun showError(content: String, onActionAccept: OnActionNotify?) {
        if (onActionAccept != null) {
            mvpActivity.showErrorAlert(
                content,
                isShowAccept = true,
                onActionAccept = onActionAccept
            )
        } else {
            mvpActivity.showErrorAlert(content)
        }
    }
}