package com.gazilla.mihail.gazillaj.helps

import android.content.SharedPreferences
import com.gazilla.mihail.gazillaj.pojo.PromoWithImg
import com.gazilla.mihail.gazillaj.pojo.UserWithKeys
import org.apache.commons.codec.binary.Hex
import java.lang.IllegalStateException
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
            return ""
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
            return ""
        } catch (e: IllegalStateException){
            e.printStackTrace()
            return ""
        }
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
        edit.putInt("myID",userWithKeys.id)
        edit.putString("publicKey", userWithKeys.publickey)
        edit.putString("privateKey",userWithKeys.privatekey)
        edit.putString("myName",userWithKeys.name)
        edit.putString("myPhone",userWithKeys.phone)
        edit.putString("myEmail",userWithKeys.email)
        edit.apply()
    }

    fun getPromoOnId(id: Int): PromoWithImg{
        App.promoFromServer.promoWithImg.forEach {
             if (it.id== id){
                return it
            }
        }
        return  App.promoFromServer.promoWithImg[0]
    }