package com.nepplus.android_okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityProfileBinding
import com.nepplus.android_okhttp.utils.GlobalData

class ProfileActivity : BaseActivity() {

    lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        profileBtn.visibility = View.GONE
        titleTxt.text = "내 정보"
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.changeNickBtn.setOnClickListener {
            var visibility = binding.changeNickEdt.visibility
            if(visibility == View.GONE){
                binding.changeNickEdt.visibility = View.VISIBLE
                binding.changeNickBtn.text = "취소"
            }else{
                binding.changeNickEdt.visibility = View.GONE
                binding.changeNickBtn.text = "닉네임 변경"
            }

        }

        binding.saveChangedBtn.setOnClickListener {
            val inputNick = binding.changeNickEdt.text.toString()
            val changedPw = binding.changePwEdt.text.toString()



        }

    }

    override fun setValues() {
        binding.currentNickTxt.text = GlobalData.loginUser!!.nickname
    }
}