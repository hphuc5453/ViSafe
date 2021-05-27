package hphuc.project.visafe_version1.core.base.domain.interactor

import android.util.Log
import kotlinex.string.getValueOrDefaultIsEmpty
import retrofit2.HttpException
import hphuc.project.visafe_version1.core.app.domain.excecutor.AndroidUseCaseExecution
import hphuc.project.visafe_version1.core.app.util.ConstApp
import hphuc.project.visafe_version1.vi_safe.domain.ReLoginUseCase
import hphuc.project.visafe_version1.vi_safe.app.config.ConfigUtil
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.PassportResponse
import hphuc.project.visafe_version1.vi_safe.app.data.network.response.base.BaseResponse
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

class ViewResponseCustomTokenResultListener<T, O>(private val resultListener: ResultListener<O>?, private val useCase: UseCase<T, O>, private val input: T) : RawResultListener<O>() {
    override fun success(data: O) {
        if (data is BaseResponse && (ConstApp.ERROR_OTP_EXPIRES.toString() == data.code.getValueOrDefaultIsEmpty()
                        || ConstApp.ERROR_OTP_HEADER_NOT_FOUND.toString() == data.code.getValueOrDefaultIsEmpty()
                        || ConstApp.ERROR_TOKEN_EXPIRES.toString() == data.code.getValueOrDefaultIsEmpty())) {
            Log.d("OkHttp dada", "response ERROR_OTP_HEADER_NOT_FOUND")
            ReLoginUseCase(AndroidUseCaseExecution()).executeAsync(object :
                ResultListener<PassportResponse> {
                override fun done() {
                }

                override fun fail(errorCode: Int, msgError: String) {
                    resultListener?.fail(errorCode, msgError)
                }

                override fun success(data: PassportResponse) {
                    if (data.success) {
                        ConfigUtil.savePassport(data)
                        ConfigUtil.saveIsFirstLoginApp(true)
                    }
                    Log.d("OkHttp dada", "recallApi  success")
                    recallApi()
                }
            }, "")
        } else {
            resultListener?.success(data)
        }
    }

    private fun recallApi() {
        resultListener?.let {
            useCase.calledAgain = true
            useCase.executeAsync(resultListener, input)
        }
    }

    override fun fail(throwable: Throwable) {
        when (throwable) {
            is UnknownHostException -> {
                resultListener?.fail(ConstApp.ERROR_CODE_UNKNOWN_HOST, useCaseResourceProvider.getErrorMsgUnknownHostException())
            }
            is HttpException -> {
//                val errorMsg = if(!BuildConfig.DEBUG){
//                    throwable.message()
//                }else{
//                    val formatURL = throwable.response()?.url().getValueOrDefaultIsEmpty().replace(ConfigURL.getApiUrl(),"")
//                    useCaseResourceProvider.getErrorMsg(formatURL,throwable.message())
//                }
                resultListener?.fail(throwable.code(), "")
            }
            is ConnectException -> {
                resultListener?.fail(ConstApp.ERROR_CODE_UNKNOWN_HOST, useCaseResourceProvider.getErrorMsgUnknownHostException())
//                resultListener.fail(ConstApp.ERROR_CODE_CONNECT, useCaseResourceProvider.getErrorMsg(throwable.message))
            }
            is SocketException -> {
                recallApi()
            }
            else -> {
                resultListener?.fail(ConstApp.ERROR_CODE_DEFAULT, useCaseResourceProvider.getErrorMsg())
            }
        }
    }

    override fun done() {
        resultListener?.done()
    }

}