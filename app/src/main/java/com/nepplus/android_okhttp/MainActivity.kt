package com.nepplus.android_okhttp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        backBtn.visibility = View.GONE
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.logutBtn.setOnClickListener {

            ContextUtil.clear(mContext)
            val myIntent = Intent(mContext, LoginActivity::class.java)
            setCustomActionBar()
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

                    Log.d("????????? ??????", topicObj.toString())

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