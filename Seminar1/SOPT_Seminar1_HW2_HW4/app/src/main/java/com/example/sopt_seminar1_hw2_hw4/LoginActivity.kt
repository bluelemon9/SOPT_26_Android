package com.example.sopt_seminar1_hw2_hw4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_join

class LoginActivity : AppCompatActivity() {
    val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener{
            if(et_id.text.isNullOrBlank() || et_pwd.text.isNullOrBlank()){
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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
}
