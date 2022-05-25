package com.nepplus.android_okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.android_okhttp.databinding.ActivityProfileBinding
import com.nepplus.android_okhttp.dialogs.CustomAlertDialog
import com.nepplus.android_okhttp.models.UserData
import com.nepplus.android_okhttp.utils.ContextUtil
import com.nepplus.android_okhttp.utils.GlobalData
import com.nepplus.android_okhttp.utils.ServerUtil
import org.json.JSONObject

class ProfileActivity : BaseActivity() {

    lateinit var binding: ActivityProfileBinding

    var currentPw = ""

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
            if (visibility == View.GONE) {
                binding.changeNickEdt.visibility = View.VISIBLE
                binding.changeNickBtn.text = "취소"
            } else {
                binding.changeNickEdt.visibility = View.GONE
                binding.changeNickBtn.text = "닉네임 변경"
            }

        }

        binding.saveChangedBtn.setOnClickListener {
            val inputNick = binding.changeNickEdt.text.toString()
            val changedPw = binding.changePwEdt.text.toString()
            currentPw = "1234"

            ServerUtil.patchRequestUserInfo(
                mContext,
                inputNick,
                currentPw,
                changedPw,
                object : ServerUtil.Companion.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {
                        val code = jsonObj.getInt("code")
                        if (code == 200) {
                            val dataObj = jsonObj.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val token = dataObj.getString("token")

                            GlobalData.loginUser = UserData().getUserDataFromJson(userObj)
                            ContextUtil.setLoginToken(mContext, token)

                            runOnUiThread {
                                binding.currentNickTxt.text = GlobalData.loginUser!!.nickname
                                Toast.makeText(mContext, "정보가 수정되었습니다", Toast.LENGTH_SHORT).show()
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

        binding.deleteBtn.setOnClickListener {
            val alert = CustomAlertDialog(mContext, this)
            alert.myDialog(
                "회원 탈퇴",
                "정말 탈퇴하시겠습니까?",
                true,
                object : CustomAlertDialog.ButtonClickListener{
                    override fun positiveBtnClicked() {
                        Toast.makeText(mContext, "탈퇴 성공", Toast.LENGTH_SHORT).show()
                    }

                    override fun negativeBtnClicked() {
                        Toast.makeText(mContext, "탈퇴 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

    }

    override fun setValues() {
        binding.currentNickTxt.text = GlobalData.loginUser!!.nickname
    }
}