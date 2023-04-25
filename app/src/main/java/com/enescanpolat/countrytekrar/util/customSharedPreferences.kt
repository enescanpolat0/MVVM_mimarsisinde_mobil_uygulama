package com.enescanpolat.countrytekrar.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class customSharedPreferences {



    companion object {

        private val PREFERENCES_TIME = "preferences_time"
        private var sharedPreferences : SharedPreferences?=null

        @Volatile private var instance : customSharedPreferences?=null
        private val lock = Any()

        operator fun invoke(context: Context):customSharedPreferences = instance?: synchronized(lock){
            instance?: makecustomSharedpreferences(context).also {
                instance=it
            }
        }

        private fun makecustomSharedpreferences(context: Context) : customSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return customSharedPreferences()
        }

    }

    fun saveTime(time:Long){


        sharedPreferences?.edit(commit=true){
            putLong(PREFERENCES_TIME,time)
        }

    }

    fun getTime()= sharedPreferences?.getLong(PREFERENCES_TIME,0)


}