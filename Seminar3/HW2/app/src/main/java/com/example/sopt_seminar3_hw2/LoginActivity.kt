package com.example.sopt_seminar3_hw2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sopt_seminar3_hw2.data.RequestLogin
import com.example.sopt_seminar3_hw2.data.ResponseLogin
import com.example.sopt_seminar3_hw2.network.RequestToServer
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    val requestToServer = RequestToServer   // 싱글톤 그대로 가져옴
    val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

            btn_login.setOnClickListener {
                if (et_id.text.isNullOrBlank() || et_pwd.text.isNullOrBlank()) {
                    Toast.makeText(this, "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    // 로그인 요청
                    requestToServer.loginservice.requestLogin(
                        RequestLogin(
                            id = et_id.text.toString(),
                            password = et_pwd.text.toString()
                        )   // 로그인 정보를 전달 (Call 타입 리턴)
                    ).enqueue(object : Callback<ResponseLogin> { // Callback 등록 (서버 통신 비동기적 요청)

                        // 비동기 요청 후 응답을 받았을 때 수행할 행동이 정의된 곳
                        override fun onFailure(call: Call<ResponseLogin>, t: Throwable){
                            // 통신 실패
                            Toast.makeText(this@LoginActivity, "통신 실패", Toast.LENGTH_SHORT).show()

                        }
                        override fun onResponse(
                            call: Call<ResponseLogin>,
                            response: Response<ResponseLogin>
                        ) {
                            // 통신 성공
                            if(response.isSuccessful){  // statusCode가 200-300 사이일 때, 응답 body 이용 가능
                                if(response.body()!!.success){  // ResponseLogin의 success가 true인 경우 -> 로그인
                                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else{
                                    Toast.makeText(this@LoginActivity, "아이디/비밀번호를 확인하세요!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }
            }

            btn_join.setOnClickListener {
                val intent = Intent(this, JoinActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }

        // 자동로그인(로그인유지)
        val loginpref = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE)
        if (loginpref.getBoolean("AUTO_LOGIN", false)) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                // 로그인 입력창에 회원가입 성공한 id와 pw가 입력되도록 함
                et_id.setText(data?.getStringExtra("id"))
                et_pwd.setText(data?.getStringExtra("pw"))

                // 자동로그인(계정 정보 저장)
                val pref = getSharedPreferences("USER", Context.MODE_PRIVATE)
                val editor = pref.edit()

                editor.putString("id", data?.getStringExtra("id"))
                editor.putString("pw", data?.getStringExtra("pw"))
                editor.commit()
            }
        }
    }
}
