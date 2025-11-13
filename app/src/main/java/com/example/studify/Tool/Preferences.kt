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
        }
    }

    fun putString(key: String?, value: String?) {
        getSharedPreferences().edit{
            putString(key, value)
        }
    }

    fun getString(key: String?): String? {
        return getSharedPreferences().getString(key, null)
    }

    fun putInt(key: String?, value: Int) {
        getSharedPreferences().edit{
            putInt(key, value)
        }

    }

    fun getInt(key: String?): Int {
        return getSharedPreferences().getInt(key, 0)
    }

    fun putBoolean(key: String?, value: Boolean) {
        val edit = getSharedPreferences().edit{
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String?): Boolean {
        return getSharedPreferences().getBoolean(key, false)
    }

    fun putStringSet(key: String?, value: kotlin.collections.Set<String?>?) {
        getSharedPreferences().edit(){
            putStringSet(key, value)
        }
    }

    fun getStringSet(key: String?): kotlin.collections.Set<String?>? {
        return getSharedPreferences().getStringSet(key, HashSet())
    }
}