package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Canvas
import android.provider.ContactsContract
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.app.util.SwipeControllerActions
import hphuc.project.visafe_version1.core.app.view.loading.Loadinger
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.ListViewMvp
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.OnItemRvClickedListener
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator
import hphuc.project.visafe_version1.vi_safe.app.util.view.bubble_layout.custom_swipe.CustomSwipeCallRight
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.model.ListContactsItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.renderer.ListContactsAlphabetItemViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.renderer.ListContactsItemViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import kotlinex.boolean.getValueOrDefault
import kotlinex.collection.getValueOrDefault
import kotlinex.mvpactivity.showErrorAlert
import kotlinex.string.getValueOrDefaultIsEmpty
import kotlinex.view.gone
import kotlinx.android.synthetic.main.layout_contacts.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*


class ListContactsView(mvpActivity: MvpActivity, viewCreator: ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), ListContactsContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        AndroidMvpView.LayoutViewCreator(R.layout.layout_contacts, context, viewGroup)

    private val mPresenter = ListContactsPresenter(mvpActivity, AndroidScreenNavigator(mvpActivity))
    private var listViewMvp: ListViewMvp? = null
    private val listData: MutableList<ViewModel> = mutableListOf()
    private val mResource = ListContactsResourceProvider(mvpActivity)

    // alphabet
    private val renderInputAlphabet = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        orientation = LinearRenderConfigFactory.Orientation.VERTICAL
    )
    private val renderConfigAlphabet = LinearRenderConfigFactory(renderInputAlphabet).create()
    private var listViewMvpAlphabet: ListViewMvp? = null
    private val listDataAlphabet: MutableList<ViewModel> = mutableListOf()

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {
        }
    })

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        showData()
    }



    // swipe right
    private val buttonAction = object : SwipeControllerActions() {

//        override fun onLeftClicked(position: Int) {
//            val data = listData[position]
//            if(data is ListContactItemViewModel){
//                layout.tvMaxLength.text = "${data.contactHintName.getValueOrDefaultIsEmpty().length}/15"
//                layout.edtName.setText(data.contactHintName.getValueOrDefaultIsEmpty())
//                dialogError?.show()
//            }
//            super.onLeftClicked(position)
//        }
    }
    private val swipeController =
        CustomSwipeCallRight(
            buttonAction,
            mvpActivity
        )

//    private val itemTouchHelper = ItemTouchHelper(swipeController)
    private val onItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            swipeController.onDraw(c, mResource.getIconCall(), mResource.getIconMessage())
        }
    }

    private val renderInput = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        decoration = onItemDecoration,
        orientation = LinearRenderConfigFactory.Orientation.VERTICAL
    )
    private val renderConfig = LinearRenderConfigFactory(renderInput).create()

    override fun initData() {
        super.initData()
        mPresenter.getData()
    }

    private val onItemRvClickedListener = object : OnItemRvClickedListener<ViewModel> {
        override fun onItemClicked(view: View, position: Int, dataItem: ViewModel) {
            when (dataItem) {
                is ListContactsItemViewModel -> {
                    val extra = ContractsDetailDataIntent(
                        id = dataItem.id,
                        name = dataItem.name,
                        phoneNumber = dataItem.phone,
                        avatar = dataItem.avatar,
                        isAdded = dataItem.isAdded
                    )
                    mPresenter.gotoContactsDetailActivity(extra)
                }
            }
        }
    }

    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        initRecycleView()
        initView()
    }

    private fun initView() {
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
        view.tvTitle.text = mResource.getTextTitle()
        view.ivBack.gone()
        if (ActivityCompat.checkSelfPermission(
                mvpActivity,
                android.Manifest.permission.READ_CONTACTS
            )
            == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                mvpActivity,
                android.Manifest.permission.WRITE_CONTACTS
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            getContactList()
        } else {
            mPresenter.checkPermission()
        }
        view.swRefresh.setOnRefreshListener(onRefreshListener)
    }

    private var idChoose : String? = null
    private val onAcceptAdd = object : OnActionNotify {
        override fun onActionNotify() {
            val data = listData.find { it is ListContactsItemViewModel && it.id == idChoose }
            if (data is ListContactsItemViewModel){
                data.isAdded = true
                listViewMvp?.notifyItemChanged(listData.indexOf(data))
                val newList = ConfigUtil.listSupport
                newList?.add(data)
                newList?.let { ConfigUtil.saveListSupport(it) }
                Utils.makeText(mvpActivity).show()
            }
        }
    }

    private val onActionAdd = object : OnActionData<ListContactsItemViewModel> {
        override fun onAction(data: ListContactsItemViewModel) {
            idChoose = data.id
            showError(mResource.getTextAcceptContact(), onAcceptAdd)
        }
    }

    override fun handleInitData(list: MutableList<ViewModel>) {
        listDataAlphabet.clear()
        listDataAlphabet.addAll(list)
        listViewMvpAlphabet?.setItems(listDataAlphabet)
        listViewMvpAlphabet?.notifyDataChanged()
    }

    override fun handleCheckPermission() {
        getContactList()
    }

    private fun initRecycleView() {
//        itemTouchHelper.attachToRecyclerView(view.rvContact)
        listViewMvp = ListViewMvp(mvpActivity, view.rvContact, renderConfig)
        listViewMvp?.addViewRenderer(ListContactsItemViewRenderer(mvpActivity, onActionAdd))
        listViewMvp?.setOnItemRvClickedListener(onItemRvClickedListener)
        listViewMvp?.createView()

        // alphabet
        listViewMvpAlphabet = ListViewMvp(mvpActivity, view.rvAlphabet, renderConfigAlphabet)
        listViewMvpAlphabet?.addViewRenderer(ListContactsAlphabetItemViewRenderer(mvpActivity))
        listViewMvpAlphabet?.createView()
    }

    private fun showData() {
        this.listData.clear()
        listData.addAll(ConfigUtil.listContacts.getValueOrDefault())
        if (ConfigUtil.listSupport != null){
            ConfigUtil.listSupport?.forEach {
                if (it is ListContactsItemViewModel){
                    val data = listData.find { contacts-> contacts is ListContactsItemViewModel && contacts.id == it.id }
                    if (data is ListContactsItemViewModel){
                        data.isAdded = true
                    }
                }
            }
        }
        listViewMvp?.setItems(listData)
        listViewMvp?.notifyDataChanged()

        hideLoading()
    }

    private fun getContactList() {
        if(ConfigUtil.listContacts.getValueOrDefault().isEmpty()){
            val cr: ContentResolver = mvpActivity.contentResolver
            val cur: Cursor? = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )
            if ((cur?.count ?: 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    val photo = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))
                    val id: String = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val name: String = cur.getString(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                        )
                    )
                    if (cur.getInt(
                            cur.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                            )
                        ) > 0
                    ) {
                        val pCur: Cursor? = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        while (pCur?.moveToNext().getValueOrDefault()) {
                            val phoneNo = pCur?.getString(
                                pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            listData.add(
                                ListContactsItemViewModel(
                                    id = id,
                                    name = name,
                                    phone = phoneNo.getValueOrDefaultIsEmpty(),
                                    avatar = photo
                                )
                            )
                        }
                        pCur?.close()
                    }
                }
            }
            cur?.close()
            ConfigUtil.saveListContacts(listData)
        }
        else{
            showData()
        }
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
        view.swRefresh.isRefreshing = false
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