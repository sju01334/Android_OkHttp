package com.nepplus.android_okhttp.models

import org.json.JSONObject
import java.io.Serializable

class TopicData : Serializable{

    var id = 0
    var title = ""
    var imageUrl = ""
    var replyCount = 0

    val sideList = ArrayList<SideData>()

//    주제 정보를 담고있는 JSONObject 가 들어오면 > TopicData로 변환해주는 메서드 생성

    companion object {
        fun getTopicDataFromJson(jsonObj : JSONObject) : TopicData{
            val topicData = TopicData()

            topicData.id = jsonObj.getInt("id")
            topicData.title = jsonObj.getString("title")
            topicData.imageUrl = jsonObj.getString("img_url")
            topicData.replyCount = jsonObj.getInt("reply_count")

            var sideArr = jsonObj.getJSONArray("sides")
            for (i in 0 until sideArr.length()){
                val sideObj = sideArr.getJSONObject(i)

                val sideData = SideData.getSideDataFromJson(sideObj)

                topicData.sideList.add(sideData)

            }


            return topicData
        }


    }

}