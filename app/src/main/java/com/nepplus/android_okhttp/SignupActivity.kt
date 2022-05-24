package com.nepplus.android_okhttp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
        binding.emailEdt.addTextChangedListener {
            binding.dupEmailTxt.text = "중복 검사를 해주세요."
        }

        binding.nicknameEdt.addTextChangedListener {
            binding.dupNickNameTxt.text = "중복 검사를 해주세요."
        }

//            [도전 과제] 이메일 / 닉네임 중복검사 통과 X => 회원가입 처리 진행 X
//            hint : return을 활용해서 클릭이벤트를 취소하자.

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

        binding.dupEmailBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val textView = binding.dupEmailTxt
            checkDuplicate("EMAIL", inputEmail, textView)
        }

        binding.dupNickNameBtn.setOnClickListener {
            val inputNick = binding.nicknameEdt.text.toString()
            val textView = binding.dupNickNameTxt
            checkDuplicate("NICK_NAME", inputNick, textView)
        }
    }

    override fun setValues() {

    }

    fun checkDuplicate(type : String, value : String, textView : TextView){
//        타입에 따른 중복 검사를 진행
        ServerUtil.getRequestUserCheck(type, value, object : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")
                runOnUiThread {
                    when (code) {
//                    중복이 아닐경우
                        200 -> { textView.text = message }
//                    중복될 경우
                        400 -> { textView.text = message }
                        else -> {}
                    }
                }
            }
        })
    }


}