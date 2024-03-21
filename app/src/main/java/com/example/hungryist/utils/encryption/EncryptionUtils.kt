package com.example.hungryist.utils.encryption

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class EncryptionUtils(val context: Context) {

    private val keyStore = KeyStore.getInstance("MyKeyStore").apply {
        load(null)
    }

    private val file: File by lazy {
        File(context.filesDir, "secret.txt").apply {
            if (!exists())
                createNewFile()
        }
    }

    private val fos: FileOutputStream by lazy {
        FileOutputStream(file)
    }

    private val fis: FileInputStream by lazy {
        FileInputStream(file)
    }

    private val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, getKey())
    }

    private fun getDecryptCipher(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private fun encrypt(textToEncrypt: String) {
        val bytes: ByteArray = textToEncrypt.encodeToByteArray()
        val encryptedBytes = encryptCipher.doFinal(bytes)
        fos.run {
            write(encryptCipher.iv.size)
            write(encryptCipher.iv)
            write(encryptedBytes.size)
            write(encryptedBytes)
        }
    }

    private fun decrypt(): String {
        return fis.run {
            val ivSize = read()
            val ivBytes = ByteArray(ivSize)
            read(ivBytes)
            val encryptedSize = read()
            val encryptedBytes = ByteArray(encryptedSize)
            read(encryptedBytes)
            getDecryptCipher(ivBytes).doFinal(encryptedBytes)
        }.decodeToString()
    }

    private fun getKey(): SecretKey {
        val secretKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        return secretKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

//    private fun retrieveSecretKey(): SecretKey? {
//        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID)
//        keyStore.load(null)
//        val entry = keyStore.getEntry(privateKeyId, null)
//        val secretKey: SecretKey? = (entry as?
//                (KeyStore.SecretKeyEntry))?.secretKey
//        return secretKey
//    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}