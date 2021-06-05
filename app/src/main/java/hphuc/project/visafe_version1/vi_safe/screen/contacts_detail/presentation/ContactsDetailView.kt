package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.ListViewMvp
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation.model.ContactsDetailHeaderItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation.renderer.ContactsDetailHeaderItemViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.model.ListContactsItemViewModel
import kotlinex.mvpactivity.showErrorAlert
import kotlinex.string.getValueOrDefaultIsEmpty
import kotlinx.android.synthetic.main.layout_contacts_detail.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class ContactsDetailView(
    mvpActivity: MvpActivity,
    viewCreator: ViewCreator,
    private val extra: ContractsDetailDataIntent?
) :
    AndroidMvpView(mvpActivity, viewCreator), ContactsDetailContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_contacts_detail, context, viewGroup)

    private val loadingView = Loadinger.create(mvpActivity, mvpActivity.window)
    private val mPresenter = ContactsDetailPresenter()
    private val mResource = ContactsDetailResourceProvider(mvpActivity)

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

    private val onAcceptAdd = object : OnActionNotify {
        override fun onActionNotify() {
            val data = listData.find { it is ContactsDetailHeaderItemViewModel }
            if (data is ContactsDetailHeaderItemViewModel) {
                data.isAdded = true
            }
            listViewMvp?.notifyItemChanged(listData.indexOf(data))
            ConfigUtil.listSupport?.add(
                ListContactsItemViewModel(
                    id = extra?.id.getValueOrDefaultIsEmpty(),
                    phone = extra?.phoneNumber.getValueOrDefaultIsEmpty(),
                    name = extra?.name.getValueOrDefaultIsEmpty(),
                    avatar = extra?.avatar.getValueOrDefaultIsEmpty()
                )
            )
            Utils.makeText(mvpActivity).show()
        }
    }

    private val onActionAdd = object : OnActionData<ContactsDetailHeaderItemViewModel> {
        override fun onAction(data: ContactsDetailHeaderItemViewModel) {
            showError(mResource.getTextAcceptContact(), onAcceptAdd)
        }
    }

    private val onActionClick = View.OnClickListener {
        when (it.id) {
            view.ivBack.id -> {
                mvpActivity.onBackPressed()
            }
        }
    }

    private fun initView() {
        view.tvTitle.text = mResource.getTextTitle()
        view.ivBack.setOnClickListener(onActionClick)
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        initView()
        initRecycleView()
    }

    private fun initRecycleView() {
        listViewMvp = ListViewMvp(mvpActivity, view.rvContactDetail, renderConfig)
        listViewMvp?.addViewRenderer(ContactsDetailHeaderItemViewRenderer(mvpActivity, onActionAdd))
        listViewMvp?.createView()
    }

    override fun showData(list: MutableList<ViewModel>) {
        listData.clear()
        listData.addAll(list)
        listViewMvp?.setItems(listData)
        listViewMvp?.notifyDataChanged()
    }

    override fun initData() {
        super.initData()
        extra?.let { mPresenter.getData(it) }
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