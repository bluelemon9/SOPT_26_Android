package com.example.sopt_seminar1_hw2_hw4.network

import com.example.sopt_seminar1_hw2_hw4.data.RequestLogin
import com.example.sopt_seminar1_hw2_hw4.data.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestInterface {
    @POST("/user/signin")
    fun requestLogin(@Body body: RequestLogin): Call<ResponseLogin>
}