package com.nepplus.android_okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityMainBinding
import com.nepplus.android_okhttp.utils.ServerUtil
import okhttp3.FormBody
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var  binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupEvents()
        setValues()

    }


    override  fun setupEvents(){
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPW = binding.passwordEdt.text.toString()

            ServerUtil.postRequestLogin(inputEmail, inputPW, object : ServerUtil.Companion.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")

                    if (code == 200) {
//                       로그인 시도 성공
                        runOnUiThread {
                            Toast.makeText(mContext, "로그인성공", Toast.LENGTH_SHORT).show()
                        }



                    } else {
                        val message = jsonObj.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, "로그인실패", Toast.LENGTH_SHORT).show()
                        }




                    }
                }

            })

        }

    }

    override fun setValues(){

    }
}