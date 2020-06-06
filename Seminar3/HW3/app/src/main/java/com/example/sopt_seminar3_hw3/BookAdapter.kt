package com.example.sopt_seminar3_hw3

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar3_hw3.data.DocumentsData

class BookAdapter(private val context: Context, var datas: List<DocumentsData>) : RecyclerView.Adapter<BookViewHolder>() {
//class BookAdapter(private val context: Context) : RecyclerView.Adapter<BookViewHolder>() {
    //var datas = mutableListOf<DocumentsData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_kakaobook, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}