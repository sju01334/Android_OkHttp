package com.nepplus.android_okhttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivitySignupBinding

class SignupActivity : BaseActivity() {

    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.signUpBtn.setOnClickListener {


        }
    }

    override fun setValues() {
        TODO("Not yet implemented")
    }

}