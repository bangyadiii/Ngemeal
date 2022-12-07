package com.ngemeal.ngemeal

import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import com.ngemeal.ngemeal.network.HttpClient

class Ngemeal : MultiDexApplication(){

    companion object{
        lateinit var instance : Ngemeal

        fun getApp() : Ngemeal {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getPreferences() : SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(this)
    }

    fun setToken(token : String) {
        getPreferences().edit().putString("PREFERENCE_TOKEN", token).apply()
        HttpClient.getInstance().buildRetrofitClient(token)
    }

    fun getToken() : String? {
        return getPreferences().getString("PREFERENCE_TOKEN", null)
    }

    fun setUser(user : String) {
        getPreferences().edit().putString("PREFERENCE_USER", user).apply()
    }

    fun getUser() : String? {
        return getPreferences().getString("PREFERENCE_USER", null)
    }

    fun unsetToken(){
        getPreferences().edit().remove("PREFERENCE_TOKEN").apply()
    }
    fun unsetUser(){
        getPreferences().edit().remove("PREFERENCE_USER").apply()
    }
}