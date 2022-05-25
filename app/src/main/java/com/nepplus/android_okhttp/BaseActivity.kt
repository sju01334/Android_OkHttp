package com.nepplus.android_okhttp

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    val TAG = javaClass.simpleName

    abstract fun setupEvents()
    abstract fun setValues()

    fun setCustomActionBar() {
//         기존 액션바를 담아줄 변수 생성
        val defaultActionBar = supportActionBar!!

//        커스텀 모드로 변경
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.my_custom_actionbar)

//        양옆 여백 제거 -> 모든 영역이 커스텀뷰
        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)
    }
}
