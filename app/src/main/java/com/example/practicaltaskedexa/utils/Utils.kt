package com.example.practicaltaskedexa.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.lang.StringBuilder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Utils {

    companion object {
        fun md5(s: String): String? {
            val MD5 = "MD5"
            try {
                // Create MD5 Hash
                val digest: MessageDigest = MessageDigest
                    .getInstance(MD5)
                digest.update(s.toByteArray())
                val messageDigest: ByteArray = digest.digest()

                // Create Hex String
                val hexString = StringBuilder()
                for (aMessageDigest in messageDigest) {
                    var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                    while (h.length < 2) h = "0$h"
                    hexString.append(h)
                }
                Log.e("hash", hexString.toString())
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo == null) {
                /*Toast.makeText(
                    context,
                    "Please check your internet connection.",
                    Toast.LENGTH_SHORT
                )
                    .show()*/
                return false
            } else if (networkInfo.isConnected) {
                return true
            }
            return false
        }
    }
}