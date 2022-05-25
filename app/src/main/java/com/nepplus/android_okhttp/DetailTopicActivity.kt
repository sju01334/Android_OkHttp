package com.nepplus.android_okhttp

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nepplus.android_okhttp.databinding.ActivityDetailTopicBinding
import com.nepplus.android_okhttp.models.TopicData
import com.nepplus.android_okhttp.utils.ServerUtil
import org.json.JSONObject

class DetailTopicActivity : BaseActivity() {

    lateinit var binding : ActivityDetailTopicBinding
    var mTopicData = TopicData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)
        mTopicData = intent.getSerializableExtra("topicData") as TopicData
        titleTxt.text = mTopicData.title
        setupEvents()
        setValues()
    }


    override fun setupEvents() {
        binding.vote1Btn.setOnClickListener {
//            서버의 투표 APi 호출
            voteSide(mTopicData.sideList[0].id)
        }

        binding.vote2Btn.setOnClickListener {
            voteSide(mTopicData.sideList[1].id)
        }
    }

    override fun setValues() {
        setTopicDataToUi()
        getTopicDeatailFromServer()
    }

    fun getTopicDeatailFromServer (){
        ServerUtil.getTopicDeatil(mContext, mTopicData.id, object  : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

                val topicData = TopicData.getTopicDataFromJson(topicObj)
                mTopicData = topicData
            }
        })
    }

    fun setTopicDataToUi (){
        binding.titleTxt.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageUrl).into(binding.backgroundImg)

        binding.side1Txt.text = mTopicData.sideList[0].title
        binding.side2Txt.text = mTopicData.sideList[1].title

        binding.vote1CountTxt.text = "${mTopicData.sideList[0].voteCount}표"
        binding.vote2CountTxt.text = "${mTopicData.sideList[1].voteCount}표"

    }

    fun voteSide(sideId : Int){
        ServerUtil.postReqeustVote(mContext, sideId , object : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")

                runOnUiThread {
                    Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()

                    if(code == 200){
                        val dataObj = jsonObj.getJSONObject("data")
                        val topicObj = dataObj.getJSONObject("topic")
                        mTopicData = TopicData.getTopicDataFromJson(topicObj)

                        setTopicDataToUi()
                    }
                }
            }
        })
    }

}