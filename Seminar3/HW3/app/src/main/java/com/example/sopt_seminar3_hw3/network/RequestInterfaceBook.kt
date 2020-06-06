package com.example.sopt_seminar3_hw3.network

import com.example.sopt_seminar3_hw3.data.ResponseBookData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RequestInterfaceBook {
    @Headers("Authorization: KakaoAK 8132143585be1dcb6c82b159286694fb")
    @GET("/v3/search/book")

    fun requestBook(@Query("query") bookTitle : String) : Call<ResponseBookData>
}