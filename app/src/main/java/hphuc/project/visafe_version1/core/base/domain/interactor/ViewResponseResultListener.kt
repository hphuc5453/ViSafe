package hphuc.project.visafe_version1.core.base.domain.interactor

import retrofit2.HttpException
import hphuc.project.visafe_version1.core.app.util.ConstApp
import java.net.ConnectException
import java.net.UnknownHostException


class ViewResponseResultListener<in T>(private val resultListener: ResultListener<T>) :
    RawResultListener<T>() {
    override fun success(data: T) {
        resultListener.success(data)
    }

    override fun fail(throwable: Throwable) {
        when (throwable) {
            is UnknownHostException -> {
                resultListener.fail(
                    ConstApp.ERROR_CODE_UNKNOWN_HOST,
                    useCaseResourceProvider.getErrorMsgUnknownHostException()
                )
            }
            is HttpException -> {
//                val errorMsg = useCaseResourceProvider.getErrorMsg(throwable.response()?.url().getValueOrDefaultIsEmpty(),throwable.message())
//                if(throwable.code() == 401){
//                    //Unauthorize
//                    //Clear and get back to login screen
////                    getBackToLoginScreen()
//                }
                resultListener.fail(throwable.code(), "")
            }
            is ConnectException -> {
                resultListener.fail(
                    ConstApp.ERROR_CODE_UNKNOWN_HOST,
                    useCaseResourceProvider.getErrorMsgUnknownHostException()
                )
//                resultListener.fail(ConstApp.ERROR_CODE_CONNECT, useCaseResourceProvider.getErrorMsg(throwable.message))
            }
            else -> {
                resultListener.fail(
                    ConstApp.ERROR_CODE_DEFAULT,
                    useCaseResourceProvider.getErrorMsg()
                )
            }
        }
    }

    override fun done() {
        resultListener.done()
    }

//    private fun getBackToLoginScreen(){
//        Toast.makeText(App.app?.applicationContext,App.app?.resources?.getString(R.string.text_error_out_login_authorize),Toast.LENGTH_SHORT).show()
//        ConfigUtil.savePassport(null)
//        val intent = Intent(App.app!!,LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        App.app!!.startActivity(intent)
//    }

}