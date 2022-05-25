package com.nepplus.android_okhttp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    lateinit var  mContext : Context
    lateinit var backBtn : ImageView
    lateinit var titleTxt : TextView
    lateinit var profileBtn : ImageView

    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
//        액션바가 존재하는지 확인후(?) > 있다면 let으로 함수 진행
        supportActionBar?.let {
            setCustomActionBar()
        }

    }

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

        backBtn = defaultActionBar.customView.findViewById(R.id.backBtn)
        titleTxt = defaultActionBar.customView.findViewById(R.id.titleTxt)
        profileBtn = defaultActionBar.customView.findViewById(R.id.profileBtn)

        backBtn.setOnClickListener {
            finish()
        }

        profileBtn.setOnClickListener {
            val myIntent = Intent(mContext, ProfileActivity::class.java)
            startActivity(myIntent)
        }


    }
}
