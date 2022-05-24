package com.nepplus.android_okhttp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

        }
    }
}