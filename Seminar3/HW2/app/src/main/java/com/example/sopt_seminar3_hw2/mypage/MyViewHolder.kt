package com.example.sopt_seminar3_hw2.mypage

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sopt_seminar3_hw2.R

class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val img_contents = itemView.findViewById<ImageView>(R.id.img_contents)

    fun bind(webtoonData: MyData){
        Glide.with(itemView).load(webtoonData.img_contents).into(img_contents)
    }
}