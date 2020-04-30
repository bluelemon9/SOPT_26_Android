package com.example.sopt_seminar1_hw2_hw4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

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
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("id", et_joinid.text.toString())
                intent.putExtra("pw", et_joinpwd.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
