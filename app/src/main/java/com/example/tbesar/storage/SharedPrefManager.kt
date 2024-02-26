package com.example.tbesar.storage

import android.content.Context
import android.content.Intent
import com.example.tbesar.LoginActivity
import com.example.tbesar.model.login.loginData
import com.example.tbesar.ui.profile.ProfileFragment


class SharedPrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: loginData
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return loginData(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("nama", null),
                sharedPreferences.getString("no_hp", null),
                sharedPreferences.getString("username", null)
            )
        }


    fun saveUser(user: loginData) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", user.id)
        editor.putString("nama", user.nama)
        editor.putString("no_ho", user.no_hp)
        editor.putString("username", user.username)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}