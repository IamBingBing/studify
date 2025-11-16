package com.example.studify.Tool

import android.content.Context
import android.content.SharedPreferences
import com.example.studify.App
import androidx.core.content.edit

object Preferences {
    private fun getSharedPreferences (): SharedPreferences{
        return App.getContext()!!.getSharedPreferences("studify", Context.MODE_PRIVATE);
    }
    fun remove(key: String?) {
        getSharedPreferences().edit {
            remove(key)
            apply()
        }
    }

    fun putString(key: String?, value: String?) {
        getSharedPreferences().edit{
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String?): String? {
        return getSharedPreferences().getString(key, null)
    }

    fun putInt(key: String?, value: Int) {
        getSharedPreferences().edit{
            putInt(key, value)
            apply()
        }

    }
    fun getInt(key: String?): Int {
        return getSharedPreferences().getInt(key, 0)
    }

    fun putFloat(key: String?, value: Int) {
        getSharedPreferences().edit{
            putFloat(key, value)
            apply()
        }

    }
    fun getFloat(key: String?): Float {
        return getSharedPreferences().getFloat(key,0f)
    }


    fun putBoolean(key: String?, value: Boolean) {
        val edit = getSharedPreferences().edit{
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(key: String?): Boolean {
        return getSharedPreferences().getBoolean(key, false)
    }

    fun putStringSet(key: String?, value: kotlin.collections.Set<String?>?) {
        getSharedPreferences().edit(){
            putStringSet(key, value)
            apply()
        }
    }

    fun getStringSet(key: String?): kotlin.collections.Set<String?>? {
        return getSharedPreferences().getStringSet(key, HashSet())
    }
}