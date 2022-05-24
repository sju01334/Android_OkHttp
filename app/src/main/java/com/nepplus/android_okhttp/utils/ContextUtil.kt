package com.nepplus.android_okhttp.utils

import android.content.Context

class ContextUtil {
    companion object{

        //메모장 이름
        private  val prefName = "OkHttpPracticePref"

        private val AUTO_LOGIN = "AUTO_LOGIN"
        private val LOGIN_TOKEN = "LOGIN_TOKEN"

        fun setLoginToken(context: Context, token : String){
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_TOKEN, token).apply()
        }

        fun getLoginToken(context: Context) : String{
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            return pref.getString(LOGIN_TOKEN, "")!!
        }


        fun setAutoLogin( context : Context, isAuto : Boolean){
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, isAuto).apply()
        }

        fun getAutoLogin( context : Context) : Boolean{
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)
            //아무것도 아닐때는 false값을 넣는다
        }

        fun clear(context : Context){
            val pref = context.getSharedPreferences(prefName , Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }


    }
}