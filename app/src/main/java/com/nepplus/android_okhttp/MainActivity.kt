package com.nepplus.android_okhttp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nepplus.android_okhttp.adapters.TopicRecyclerAdapter
import com.nepplus.android_okhttp.databinding.ActivityMainBinding
import com.nepplus.android_okhttp.models.TopicData
import com.nepplus.android_okhttp.utils.ContextUtil
import com.nepplus.android_okhttp.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var mTopicAdapter : TopicRecyclerAdapter

    var mTopicList = ArrayList<TopicData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("위치정보_Main", TAG)
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
        getTopicListFromServer()

        mTopicAdapter = TopicRecyclerAdapter(mContext, mTopicList)
        binding.topicRecyclerView.adapter = mTopicAdapter
        binding.topicRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    fun getTopicListFromServer(){
        ServerUtil.getRequestMainInfo(mContext, object  : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                var dataObj = jsonObj.getJSONObject("data")
                var topicArr = dataObj.getJSONArray("topics")

                for(i in 0 until topicArr.length()){
                    val topicObj = topicArr.getJSONObject(i)

                    Log.d("받아낸 주제", topicObj.toString())

                    val topicData = TopicData.getTopicDataFromJson(topicObj)

                    mTopicList.add(topicData)

                    runOnUiThread {
                        mTopicAdapter.notifyDataSetChanged()
                    }

                }
            }
        })
    }

}