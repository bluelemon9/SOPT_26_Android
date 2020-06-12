package com.example.sopt_seminar1_hw2_hw4.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RequestToServer {
    var retrofit = Retrofit.Builder()
        .baseUrl("http://13.209.144.115:3333")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service : RequestInterface = retrofit.create(RequestInterface::class.java)
    val joinservice : RequestJoinInterface = retrofit.create(RequestJoinInterface::class.java)
}