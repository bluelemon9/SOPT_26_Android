package com.example.sopt_seminar3_hw3

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sopt_seminar3_hw3.data.DocumentsData

class BookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
    val tv_contents = itemView.findViewById<TextView>(R.id.tv_contents)
    val tv_authors = itemView.findViewById<TextView>(R.id.tv_authors)
    val img_thumbnail = itemView.findViewById<ImageView>(R.id.img_thumbnail)

    fun bind(bookData : DocumentsData) {
        tv_title.text = bookData.title
        tv_contents.text = bookData.contents
        tv_authors.text = "저자: " + bookData.authors[0]
        Glide.with(itemView).load(bookData.thumbnail).into(img_thumbnail)
    }
}