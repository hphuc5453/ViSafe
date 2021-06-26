package hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionData
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.LinearRenderConfigFactory
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.list.ListViewMvp
import hphuc.project.visafe_version1.vi_safe.app.Utils
import hphuc.project.visafe_version1.vi_safe.app.common.AccidentType
import hphuc.project.visafe_version1.vi_safe.app.common.SettingsRoleData
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.lifecycle.EventBusLifeCycle
import hphuc.project.visafe_version1.vi_safe.screen.main.MainActivity
import hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.model.SettingsRoleItemViewModel
import hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.renderer.SettingsRoleItemViewRenderer
import kotlinex.mvpactivity.showErrorAlert
import kotlinx.android.synthetic.main.layout_settings_role.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class SettingsRoleView(mvpActivity: MvpActivity, viewCreator: ViewCreator) :
    AndroidMvpView(mvpActivity, viewCreator), SettingsRoleContract.View {

    //Create view layout
    class ViewCreator(context: Context, viewGroup: ViewGroup?) :
        LayoutViewCreator(R.layout.layout_settings_role, context, viewGroup)

    private val mResource = SettingsRoleResourceProvider(mvpActivity)
    private val mPresenter = SettingsRolePresenter(mResource)

    private var listViewMvp: ListViewMvp? = null
    private val listData: MutableList<ViewModel> = mutableListOf()

    private val renderInput = LinearRenderConfigFactory.Input(
        context = mvpActivity,
        orientation = LinearRenderConfigFactory.Orientation.VERTICAL
    )
    private val renderConfig = LinearRenderConfigFactory(renderInput).create()
    private var typeChoose = AccidentType.DISASTER.value

    private val eventBusLifeCycle = EventBusLifeCycle(object : OnActionData<EventBusData> {
        override fun onAction(data: EventBusData) {
        }
    })

    private val onChangeChoose = object : OnActionData<SettingsRoleItemViewModel>{
        override fun onAction(data: SettingsRoleItemViewModel) {
            val list = mutableListOf<SettingsRoleData>()
            list.addAll(ConfigUtil.listRoleSettings)
            list.find { it.accidentType == data.typeAccident && it.type == data.type }?.isChoose = data.isChoose
            ConfigUtil.saveListRoleSettings(list)
            setDataForType()
        }

    }
    private fun initRecycleView(){
        listViewMvp = ListViewMvp(mvpActivity, view.rvSettingsRole, renderConfig)
        listViewMvp?.addViewRenderer(SettingsRoleItemViewRenderer(mvpActivity, mResource, onChangeChoose))
        listViewMvp?.createView()
    }

    private val onActionClick = View.OnClickListener {
        when(it.id){
            view.ivBack.id->{
                mvpActivity.onBackPressed()
            }
            view.ivDisaster.id->{
                typeChoose = AccidentType.DISASTER.value
                setArrowChoose()
                setDataForType()
            }
            view.ivAccident.id->{
                typeChoose = AccidentType.ACCIDENT.value
                setArrowChoose()
                setDataForType()
            }
            view.ivCrime.id->{
                typeChoose = AccidentType.CRIME.value
                setArrowChoose()
                setDataForType()
            }
            view.ivVehicle.id->{
                typeChoose = AccidentType.VEHICLE.value
                setArrowChoose()
                setDataForType()
            }

        }
    }

    private fun setArrowChoose(){
        val constraintSet = ConstraintSet()
        constraintSet.clone(view.clContainer)
        constraintSet.clear(view.ivArrow.id, ConstraintSet.TOP)
        constraintSet.clear(view.ivArrow.id, ConstraintSet.BOTTOM)
        when(typeChoose){
            AccidentType.DISASTER.value->{
                constraintSet.connect(view.ivArrow.id, ConstraintSet.TOP, view.ivDisaster.id, ConstraintSet.TOP)
                constraintSet.connect(view.ivArrow.id, ConstraintSet.BOTTOM, view.ivDisaster.id, ConstraintSet.BOTTOM)
            }
            AccidentType.ACCIDENT.value->{
                constraintSet.connect(view.ivArrow.id, ConstraintSet.TOP, view.ivAccident.id, ConstraintSet.TOP)
                constraintSet.connect(view.ivArrow.id, ConstraintSet.BOTTOM, view.ivAccident.id, ConstraintSet.BOTTOM)
            }
            AccidentType.VEHICLE.value->{
                constraintSet.connect(view.ivArrow.id, ConstraintSet.TOP, view.ivVehicle.id, ConstraintSet.TOP)
                constraintSet.connect(view.ivArrow.id, ConstraintSet.BOTTOM, view.ivVehicle.id, ConstraintSet.BOTTOM)
            }
            AccidentType.CRIME.value->{
                constraintSet.connect(view.ivArrow.id, ConstraintSet.TOP, view.ivCrime.id, ConstraintSet.TOP)
                constraintSet.connect(view.ivArrow.id, ConstraintSet.BOTTOM, view.ivCrime.id, ConstraintSet.BOTTOM)
            }
        }
        constraintSet.applyTo(view.clContainer)
    }

    private fun initView(){
        Utils.setPaddingStatusBar(view.clContainer, mvpActivity)
        view.ivBack.setOnClickListener(onActionClick)
        view.ivDisaster.setOnClickListener(onActionClick)
        view.ivAccident.setOnClickListener(onActionClick)
        view.ivCrime.setOnClickListener(onActionClick)
        view.ivVehicle.setOnClickListener(onActionClick)
        view.tvTitle.text = mResource.getTextTitle()
    }


    override fun initCreateView() {
        addLifeCycle(eventBusLifeCycle)
        initView()
        initRecycleView()
    }

    private fun setDataForType(){
        val list = mutableListOf<ViewModel>()
        listData.forEach {
            if (it is SettingsRoleItemViewModel && it.typeAccident == typeChoose){
                list.add(it)
            }
        }
        listViewMvp?.setItems(list)
        listViewMvp?.notifyDataChanged()
    }

    override fun showData(list: MutableList<ViewModel>) {
        listData.clear()
        listData.addAll(list)
        setDataForType()
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