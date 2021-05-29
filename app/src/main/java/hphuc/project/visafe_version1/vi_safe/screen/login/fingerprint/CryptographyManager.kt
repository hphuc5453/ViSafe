package hphuc.project.visafe_version1.vi_safe.screen.login.fingerprint

import javax.crypto.Cipher

interface CryptographyManager {
    fun getInitializedCipherForEncryption(keyName: String): Cipher
}

