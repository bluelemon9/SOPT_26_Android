package com.example.sopt_seminar3_hw3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServerBook {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val bookservice : RequestInterfaceBook = retrofit.create(RequestInterfaceBook::class.java)
}