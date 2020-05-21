package com.example.sopt_seminar1_hw2_hw4.data

data class ResponseLogin(
    val status: Int,
    val success : Boolean,
    val message : String,
    val data: SomeData?
)

data class SomeData(
    val jwt : String
)