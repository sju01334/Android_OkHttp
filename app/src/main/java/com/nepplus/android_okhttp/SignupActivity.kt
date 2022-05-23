package com.nepplus.android_okhttp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivitySignupBinding
import com.nepplus.android_okhttp.utils.ServerUtil
import org.json.JSONObject

class SignupActivity : BaseActivity() {

    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        setupEvents()
//        setValues()
    }

    override fun setupEvents() {
        binding.signUpBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()
            val inputNickname = binding.nicknameEdt.text.toString()

            ServerUtil.putRequestSignup(inputEmail, inputPw, inputNickname, object : ServerUtil.Companion.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")

                    if(code == 200){
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")

                        runOnUiThread {
                            Toast.makeText(mContext, "${nickname}님 가입을 환영합니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    }else {
                        val message = jsonObj.getString("message")
                        Toast.makeText(mContext, "tlfvotkdb : $message", Toast.LENGTH_SHORT).show()
                    }
                }

            })


        }
    }

    override fun setValues() {
        TODO("Not yet implemented")
    }

}