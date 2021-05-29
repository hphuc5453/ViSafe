package hphuc.project.visafe_version1.vi_safe.screen.login.fingerprint

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants.Companion.ANDROID_KEYSTORE
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants.Companion.ENCRYPTION_ALGORITHM
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants.Companion.ENCRYPTION_BLOCK_MODE
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants.Companion.ENCRYPTION_PADDING
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants.Companion.KEY_SIZE
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class CryptographyManagerImpl : CryptographyManager {

    private fun getCipher(): Cipher {
        val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"
        return Cipher.getInstance(transformation)
    }

    private fun getOrCreateSecretKey(keyName: String): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed
//        keyStore.getKey(keyName, null)?.let { return it as SecretKey }

        // if you reach here, then a new SecretKey must be generated for that keyName
        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        paramsBuilder.apply {
            setBlockModes(ENCRYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCRYPTION_PADDING)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(true)
        }

        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        keyGenerator.init(keyGenParams)
        return keyGenerator.generateKey()
    }

    override fun getInitializedCipherForEncryption(keyName: String): Cipher {
        val cipher = getCipher()
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }
}