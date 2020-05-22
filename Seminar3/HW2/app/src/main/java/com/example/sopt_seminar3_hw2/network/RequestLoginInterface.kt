package com.example.sopt_seminar3_hw2.network

import com.example.sopt_seminar3_hw2.data.RequestLogin
import com.example.sopt_seminar3_hw2.data.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestLoginInterface {
    @POST("/user/signin")
    fun requestLogin(@Body body: RequestLogin): Call<ResponseLogin>
}