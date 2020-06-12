package com.example.sopt_seminar1_hw2_hw4

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로그인 한 상태 저장
        val loginpref = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE)
        val editor = loginpref.edit()
        editor.putBoolean("AUTO_LOGIN", true)
        editor.apply()
    }
}
