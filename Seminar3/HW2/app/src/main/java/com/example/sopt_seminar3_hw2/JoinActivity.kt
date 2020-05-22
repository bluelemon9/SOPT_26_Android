package com.example.sopt_seminar3_hw2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sopt_seminar3_hw2.data.RequestJoin
import com.example.sopt_seminar3_hw2.data.ResponseJoin
import com.example.sopt_seminar3_hw2.network.RequestToServer
import kotlinx.android.synthetic.main.activity_join.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {
    val requestToServer = RequestToServer   // 싱글톤 가져옴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        btn_join.setOnClickListener {
            if(et_joinid.text.isNullOrBlank() || et_joinpwd.text.isNullOrBlank() || et_joinpwd2.text.isNullOrBlank()){
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else if(et_joinpwd.text.toString() != et_joinpwd2.text.toString()){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else{
                // 회원가입 요청
                requestToServer.joinservice.requestJoin(
                    RequestJoin(
                        id = et_joinid.text.toString(),
                        password = et_joinpwd.text.toString(),
                        name = et_name.text.toString(),
                        email = et_email.text.toString(),
                        phone = et_phone.text.toString()
                    )   // 회원가입 정보를 전달
                ).enqueue(object : Callback<ResponseJoin> { // Callback 등록, Retrofit의 Callback을 import
                    override fun onFailure(call: Call<ResponseJoin>, t: Throwable){
                        // 통신 실패
                        Toast.makeText(this@JoinActivity, "통신 실패", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseJoin>,
                        response: Response<ResponseJoin>
                    ) {
                        // 통신 성공
                        if(response.isSuccessful){  // statusCode가 200-300 사이일 때, 응답 body 이용 가능
                            if(response.body()!!.success){  // ResponseJoin의 success가 true인 경우 -> 로그인
                                Toast.makeText(this@JoinActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@JoinActivity, MainActivity::class.java)
                                intent.putExtra("id", et_joinid.text.toString())
                                intent.putExtra("pw", et_joinpwd.text.toString())
                                startActivity(intent)
                                finish()
                            } else{
                                Toast.makeText(this@JoinActivity, "다시 한번 확인하세요!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }
}