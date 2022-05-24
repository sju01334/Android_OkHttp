package com.nepplus.android_okhttp

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    val TAG = javaClass.simpleName

    abstract fun setupEvents()

    abstract  fun setValues()
}