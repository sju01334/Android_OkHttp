package com.nepplus.android_okhttp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityMainBinding
import com.nepplus.android_okhttp.utils.ContextUtil

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.logutBtn.setOnClickListener {

            ContextUtil.clear(mContext)
            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)
            finish()


        }


    }

    override fun setValues() {

    }

}