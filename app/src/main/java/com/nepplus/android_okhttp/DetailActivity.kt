package com.nepplus.android_okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setupEvents()
        setValues()
    }

    lateinit var binding : ActivityDetailBinding
    override fun setupEvents() {

    }

    override fun setValues() {

    }

}