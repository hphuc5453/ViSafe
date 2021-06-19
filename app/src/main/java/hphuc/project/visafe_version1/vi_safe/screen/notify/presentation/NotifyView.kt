package hphuc.project.visafe_version1.vi_safe.screen.notify.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.ListViewMvp
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import hphuc.project.visafe_version1.vi_safe.screen.notify.presentation.renderer.NotifyItemViewRenderer
import kotlinex.mvpactivity.showErrorAlert
import kotlinx.android.synthetic.main.layout_notify.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class NotifyView(mvpActivity: MvpActivity, viewCreator: LayoutViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), NotifyContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        LayoutViewCreator(R.layout.layout_notify, context, viewGroup)

    private val mResource = NotifyResourceProvider(mvpActivity)
    private val mPresenter = NotifyPresenter()

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

    override fun initData() {
        super.initData()
        mPresenter.getData()
    }

    private fun initView(){
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
        view.tvTitle.text = mResource.getTextTitle()
        view.ivBack.setOnClickListener(onActionClick)
    }

    private fun initRecycleView(){
        listViewMvp = ListViewMvp(mvpActivity, view.rvNotify, renderConfig)
        listViewMvp?.addViewRenderer(NotifyItemViewRenderer(mvpActivity))
        listViewMvp?.createView()
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