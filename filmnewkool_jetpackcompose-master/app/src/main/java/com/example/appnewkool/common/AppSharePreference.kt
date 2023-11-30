package com.example.appnewkool.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharePreference @Inject constructor(@ApplicationContext private val context: Context) {
    companion object{
        const val APP_SHARE_KEY = "NewKool"
    }

    fun getSharedPreferences(): SharedPreferences?{
        return context.getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE)
    }

    fun getEditor()=getSharedPreferences()?.edit()
}