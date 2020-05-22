package com.example.sopt_seminar3_hw2.network

import com.example.sopt_seminar3_hw2.data.RequestJoin
import com.example.sopt_seminar3_hw2.data.ResponseJoin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestJoinInterface {
    @POST("/user/signup")
    fun requestJoin(@Body body: RequestJoin): Call<ResponseJoin>
}