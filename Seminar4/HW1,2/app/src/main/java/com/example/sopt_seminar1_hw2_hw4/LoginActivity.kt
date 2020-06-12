package com.example.sopt_seminar1_hw2_hw4

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.sopt_seminar1_hw2_hw4.data.RequestLogin
import com.example.sopt_seminar1_hw2_hw4.data.ResponseLogin
import com.example.sopt_seminar1_hw2_hw4.network.RequestToServer
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

        onBackPressed()

        //﻿ addTextChangedListener 확장함수 사용 (4차 세미나)
        et_id.textChangedListener {s ->
            if(s.isNullOrBlank()){ //it을 원하는 이름으로 명시 가능(s로 해보자)
                nullcheck1.visibility = VISIBLE   // 빈칸이면 빨간 동그라미 표시
                showToast(("아이디가 빈칸이네요"))
            }
        }
        //위의 코드로 et_id.addTextChangedListener(object: TextWatcher){ }을 대신할 수 있음.


        btn_login.setOnClickListener{
            if(et_id.text.isNullOrBlank() || et_pwd.text.isNullOrBlank()){
                //Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                showToast("아이디와 비밀번호를 입력하세요!!")     // 4차 세미나 확장함수 사용
                nullcheck1.visibility = VISIBLE
                nullcheck2.visibility = VISIBLE
            }
            else{
/*                // 로그인 요청
                requestToServer.service.requestLogin(
                    RequestLogin(
                        id = et_id.text.toString(),
                        password = et_pwd.text.toString()
                    )   // 로그인 정보를 전달
                ).customEnqueue(
                    onError = {showToast("올바르지 못한 요청입니다")},
                    onSuccess = {
                        if(it.success){  // ResponseLogin의 success가 true인 경우 -> 로그인
                            showToast("로그인 성공")     // 4차 세미나 확장함수 사용
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else{
                            showToast("아이디/비밀번호를 확인하세요!")       // 4차 세미나 확장함수 사용
                        }
                    }
                )*/
 //4차 세미나 customEnqueue로 확장함수 사용했으므로 삭제가능

                // 로그인 요청
                requestToServer.service.requestLogin(
                    RequestLogin(
                        id = et_id.text.toString(),
                        password = et_pwd.text.toString()
                    )   // 로그인 정보를 전달
                ).enqueue(object : Callback<ResponseLogin>{ // Callback 등록, Retrofit의 Callback을 import
                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable){
                        // 통신 실패
                    }

                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                        // 통신 성공
                        if(response.isSuccessful){  // statusCode가 200-300 사이일 때, 응답 body 이용 가능
                            if(response.body()!!.success){  // ResponseLogin의 success가 true인 경우 -> 로그인
                                //Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                                showToast("로그인 성공")     // 4차 세미나 확장함수 사용
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else{
                                //Toast.makeText(this@LoginActivity, "아이디/비밀번호를 확인하세요!", Toast.LENGTH_SHORT).show()
                                showToast("아이디/비밀번호를 확인하세요!")       // 4차 세미나 확장함수 사용
                            }
                        }
                    }
                })

//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }
        }

        btn_join.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        // 자동로그인 -> 종료 시 다시 접속해도 로그인 유지
        val loginpref = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE)
        if(loginpref.getBoolean("AUTO_LOGIN", false)){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                // 로그인 입력창에 회원가입 성공한 id와 pw가 입력되도록 함
                et_id.setText(data?.getStringExtra("id"))
                et_pwd.setText(data?.getStringExtra("pw"))

                // 자동로그인 -> 계정 정보 저장
                // Context를 통해 접근, "USER"이라는 이름으로 파일이 저장되며 Editor를 이용해 값 지정
                val pref = getSharedPreferences("USER", Context.MODE_PRIVATE)
                val editor = pref.edit()

                editor.putString("id", data?.getStringExtra("id"))
                editor.putString("pw", data?.getStringExtra("pw"))
                editor.commit()
            }
        }
    }

    // 다이얼로그 커스텀
    override fun onBackPressed() {
        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        val builder = AlertDialog.Builder(this)
        val alBuilder: LayoutInflater = LayoutInflater.from(this)
        val dialogView: View = alBuilder.inflate(R.layout.custom_dialog, null)

        val btn_yes : Button = dialogView.findViewById(R.id.btn_yes)
        val btn_no : Button = dialogView.findViewById(R.id.btn_no)

//        // Dialog 사이즈 조절 하기
//        val params: WindowManager.LayoutParams = builder.getWindow().getAttributes
//        params.width = 500
//        params.height = 400
//        val window: Window = dialogView.getWindow()
//        window.setAttributes(params)


        btn_yes.setOnClickListener{
            finish()
        }
        btn_no.setOnClickListener{
            return@setOnClickListener
        }
        builder.setView(dialogView)
        builder.show()
    }

//    // 다이얼로그 기본형
//    override fun onBackPressed() {
//        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
//        val alBuilder = AlertDialog.Builder(this)
//        alBuilder.setTitle("프로그램 종료")
//        alBuilder.setMessage("종료하시겠습니까?")
//
//        alBuilder.setPositiveButton("예"){dialog, which ->
//            finish()
//        }
//        alBuilder.setNegativeButton("아니오"){dialog, which ->
//            return@setNegativeButton
//        }
//
//        alBuilder.show() // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
//    }
}
