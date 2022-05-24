package com.nepplus.android_okhttp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nepplus.android_okhttp.DetailTopicActivity
import com.nepplus.android_okhttp.R
import com.nepplus.android_okhttp.models.TopicData

class TopicRecyclerAdapter(var mContext : Context, val mList : List<TopicData>) : RecyclerView.Adapter<TopicRecyclerAdapter.MyVeiwHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVeiwHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.topic_list_item, parent, false)
        return MyVeiwHolder(row)
    }

    override fun onBindViewHolder(holder: MyVeiwHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyVeiwHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(item : TopicData){
            val titleTxt = itemView.findViewById<TextView>(R.id.titleTxt)
            val replyCountTxt = itemView.findViewById<TextView>(R.id.replyCountTxt)
            val backgroundImg = itemView.findViewById<ImageView>(R.id.backgroundImg)

            titleTxt.text = item.title
            replyCountTxt.text = "${item.replyCount}명 참여중"
            Glide.with(mContext)
                .load(item.imageUrl)
                .into(backgroundImg)

            itemView.setOnClickListener {
                val myIntent = Intent(mContext, DetailTopicActivity::class.java)
                myIntent.putExtra("topicData", item)
                mContext.startActivity(myIntent)
            }

        }
    }
}