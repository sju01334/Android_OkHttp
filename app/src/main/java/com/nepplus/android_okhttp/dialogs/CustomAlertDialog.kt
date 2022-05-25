package com.nepplus.android_okhttp.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.nepplus.android_okhttp.R
import com.nepplus.android_okhttp.databinding.CustomAlertDialogBinding

class CustomAlertDialog (val context : Context, val activity : Activity) {

    //    다이얼로그 객체 변수에 생성
    private val dialog = Dialog(context)

    lateinit var binding : CustomAlertDialogBinding

    fun myDialog (title : String, body: String, isDelete : Boolean,  onClickListener : ButtonClickListener) {
        binding = CustomAlertDialogBinding.inflate(activity.layoutInflater)
        dialog.setContentView(binding.root)

//        다이얼로그 외곽 클릭시 취소 될건지 아닌지.
        dialog.setCancelable(true)

//        다이얼로그 가로 / 세로 비율 정리

        dialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        binding.titleTxt.text = title
        binding.bodyTxt.text = body
        if (isDelete) {
//        [응용문제]
//        실행될때 환인 버튼의 색상을 red (#ff0000) 변경 > 구글링
            binding.positiveBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
        }

        binding.positiveBtn.setOnClickListener {
            onClickListener.positiveBtnClicked()
        }

        binding.negativeBtn.setOnClickListener {
            onClickListener.negativeBtnClicked()
        }

        dialog.show()
    }

    interface ButtonClickListener {
        fun positiveBtnClicked()
        fun negativeBtnClicked()
    }

}