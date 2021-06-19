package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.ListViewMvp
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.OnItemRvClickedListener
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model.SettingsMethodItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer.SettingsHeaderItemViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer.SettingsLineItemViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer.SettingsMethodItemViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.renderer.SettingsPremiumItemViewRenderer
import kotlinex.mvpactivity.showErrorAlert
import kotlinx.android.synthetic.main.layout_settings.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class SettingsView(mvpActivity: MvpActivity, viewCreator: ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), SettingsContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        LayoutViewCreator(R.layout.layout_settings, context, viewGroup)

    private val mResource = SettingsResourceProvider(mvpActivity)
    private val mPresenter = SettingsPresenter(mResource, AndroidScreenNavigator(mvpActivity))

    private var listViewMvp: ListViewMvp? = null
    private val listData: MutableList<ViewModel> = mutableListOf()

    private val renderInput = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        orientation = LinearRenderConfigFactory.Orientation.VERTICAL
    )
    private val renderConfig = LinearRenderConfigFactory(renderInput).create()

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {
        }
    })

    private val onActionClick = View.OnClickListener {
        when(it.id){
            view.ivBack.id->{
                mvpActivity.onBackPressed()
            }
        }
    }

    private val onActionPremium = object : OnActionNotify{
        override fun onActionNotify() {

        }
    }

    private val onItemRvClickedListener = object : OnItemRvClickedListener<ViewModel>{
        override fun onItemClicked(view: View, position: Int, dataItem: ViewModel) {
            when(dataItem){
                is SettingsMethodItemViewModel->{
                    when(dataItem.type){
                        SettingsMethodItemViewModel.Type.LOGOUT.value->{
                            mPresenter.logout()
                        }
                    }
                }
            }
        }

    }

    private fun initRecycleView(){
        listViewMvp = ListViewMvp(mvpActivity, view.rvSettings, renderConfig)
        listViewMvp?.addViewRenderer(SettingsHeaderItemViewRenderer(mvpActivity))
        listViewMvp?.addViewRenderer(SettingsMethodItemViewRenderer(mvpActivity))
        listViewMvp?.addViewRenderer(SettingsLineItemViewRenderer(mvpActivity))
        listViewMvp?.addViewRenderer(SettingsPremiumItemViewRenderer(mvpActivity, onActionPremium))
        listViewMvp?.setOnItemRvClickedListener(onItemRvClickedListener)
        listViewMvp?.createView()
    }

    private fun initView(){
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
        view.ivBack.setOnClickListener(onActionClick)
        view.tvTitle.text = mResource.getTextTitle()
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        initView()
        initRecycleView()
    }

    override fun showData(list: MutableList<ViewModel>) {
        listData.clear()
        if (list.isNotEmpty()){
            listData.addAll(list)
        }
        listViewMvp?.setItems(listData)
        listViewMvp?.notifyDataChanged()
    }

    override fun initData() {
        super.initData()
        mPresenter.getData()
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
        MainActivity.showLoading()
    }

    override fun hideLoading() {
        MainActivity.hideLoading()
    }

    override fun showToast(message: String) {
        Toast.makeText(mvpActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(content: String) {
        mvpActivity.showErrorAlert(content)
    }
}