package hphuc.project.visafe_version1.vi_safe.screen.login.fingerprint

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants

object BiometricPromptUtils {
    fun createBiometricPrompt(
        activity: AppCompatActivity,
        onAuthenticationSucceeded: () -> Unit,
        onAuthenticationFailed: () -> Unit,
        onAuthenticationError: () -> Unit,
        onAuthenticationCancel: () -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthenticationSucceeded()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
//                onAuthenticationFailed()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == AppConstants.CANCEL_FINGERPRINT || errorCode == AppConstants.CANCEL_FINGERPRINT_WITH_TOUCH_OUTSIZE) {
                    onAuthenticationCancel()
                } else {
                    onAuthenticationError()
                }
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

    fun createPromptInfo(
        title: String,
        subTile: String,
        des: String,
        closeText: String
    ): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(title)
            setSubtitle(subTile)
            setDescription(des)
            setConfirmationRequired(false)
            setNegativeButtonText(closeText)
        }.build()
}