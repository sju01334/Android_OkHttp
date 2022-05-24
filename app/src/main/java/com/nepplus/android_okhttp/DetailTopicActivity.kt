package com.nepplus.android_okhttp

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityDetailTopicBinding

class DetailTopicActivity : BaseActivity() {

    lateinit var binding : ActivityDetailTopicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

    }

    override fun setValues() {

    }

}