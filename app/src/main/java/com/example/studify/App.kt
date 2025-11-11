package com.example.studify

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object{
        private var context: App? = null
        fun getContext(): Context?
        {
            return context
        }
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        context = this
    }
}