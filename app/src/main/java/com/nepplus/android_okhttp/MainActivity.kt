package com.nepplus.android_okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityMainBinding
import com.nepplus.android_okhttp.utils.ServerUtil
import okhttp3.FormBody

class MainActivity : AppCompatActivity() {

    lateinit var  binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupEvents()
        setValues()

    }

    fun setupEvents(){
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPW = binding.passwordEdt.text.toString()

            ServerUtil.postRequestLogin(inputEmail, inputPW)

        }

    }

    fun setValues(){

    }
}