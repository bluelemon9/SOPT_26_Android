package com.example.sopt_seminar2_hw3

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WebtoonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val tv_webtoonname = itemView.findViewById<TextView>(R.id.tv_webtoonname)
    val tv_rate = itemView.findViewById<TextView>(R.id.tv_rate)
    val img_contents = itemView.findViewById<ImageView>(R.id.img_contents)

    fun bind(webtoonData: WebtoonData){
        tv_webtoonname.text = webtoonData.webtoonName
        tv_rate.text = webtoonData.rate
        Glide.with(itemView).load(webtoonData.img_contents).into(img_contents)
    }
}