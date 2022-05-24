package com.nepplus.android_okhttp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.nepplus.android_okhttp.utils.ContextUtil
import com.nepplus.android_okhttp.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }
    override fun setValues() {
        var myHandler = Handler(Looper.getMainLooper())

        var isTokenOk = false

        ServerUtil.getRequestUserInfo(mContext, object : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                isTokenOk = (code == 200)
            }
        })

        myHandler.postDelayed({
//            자동로그인을 해도 되는가?
            val isAutoLoginOk = ContextUtil.getAutoLogin(mContext)
//            1) 사용자가 자동로그인을 한다고 했는가?
//            2) token이 있는가?
//            2-1) 저장된 토큰이 있는지
//            2-2) 그 토큰이 유효한지


            val myIntent : Intent

            if(isAutoLoginOk && isTokenOk){
                myIntent  = Intent(mContext, MainActivity::class.java)
            }else {
                myIntent = Intent(mContext, LoginActivity::class.java)
            }

            startActivity(myIntent)
            finish()


        }, 2500)
    }
}