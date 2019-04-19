package com.gazilla.mihail.gazillaj.kotlin.helps

import android.content.SharedPreferences
import android.util.Log
import com.gazilla.mihail.gazillaj.kotlin.pojo.UserWithKeys
import org.apache.commons.codec.binary.Hex
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


     fun signatur(key: String, data: String): String {
        try {
            val sha256_HMAC = Mac.getInstance("HmacSHA256")
            val secret_key = SecretKeySpec(key.toByteArray(charset("UTF-8")), "HmacSHA256")
            sha256_HMAC.init(secret_key)
            return String(Hex.encodeHex(sha256_HMAC.doFinal(data.toByteArray(charset("UTF-8")))))

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }
        return ""
    }

    fun checkFormatPhone(phone: String?): String {
        var s = phone

        return if (s == null || s == "")
            "" // пусте поле

        else if (s.length < 10 || s.length > 12)
            ""
        else if (s[0] == '8' && s.length == 11) {
            s = s.substring(1)
            s
        } else if (s[0] == '+' && s[1] == '7' && s.length == 12) {
            s = s.substring(2)
            s
        } else if (s[0] =='9'&&s.length==10){
            s
        }else
            ""
    }

    fun saveUserInfoIntoSharedPreferences(sharedPreferences: SharedPreferences, userWithKeys: UserWithKeys){
        val edit = sharedPreferences.edit()
        edit.putInt("APP_PREF_MY_ID",userWithKeys.id)
        edit.putString("APP_PREF_PUBLIC_KEY", userWithKeys.publickey)
        edit.putString("APP_PREF_PRIVATE_KEY",userWithKeys.privatekey)
        edit.putString("APP_PREF_MY_NAME",userWithKeys.name)
        edit.putString("APP_PREF_MY_PHONE",userWithKeys.phone)
        edit.putString("APP_PREF_MY_EMAIL",userWithKeys.email)
        edit.apply()
    }