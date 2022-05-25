package com.nepplus.android_okhttp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityLoginBinding
import com.nepplus.android_okhttp.models.UserData
import com.nepplus.android_okhttp.utils.ContextUtil
import com.nepplus.android_okhttp.utils.GlobalData
import com.nepplus.android_okhttp.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var  binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupEvents()
        setValues()

    }


    override  fun setupEvents(){
        binding.autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            ContextUtil.setAutoLogin(mContext, isChecked)
        }

        binding.signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignupActivity::class.java)
            startActivity(myIntent)
        }
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPW = binding.passwordEdt.text.toString()

            ServerUtil.postRequestLogin(inputEmail, inputPW, object : ServerUtil.Companion.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")

                    if (code == 200) {
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")
                        val token = dataObj.getString("token")

                        GlobalData.loginUser = UserData().getUserDataFromJson(userObj)

                        ContextUtil.setLoginToken(mContext, token)

                        runOnUiThread {
                            Toast.makeText(mContext, "${GlobalData.loginUser!!.nickname}님 환영합니다.", Toast.LENGTH_SHORT).show()

                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }


                    } else {
                        val message = jsonObj.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            })

        }

    }
    override fun setValues(){
        binding.autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)
    }
}