package com.example.sopt_seminar3_hw3

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sopt_seminar3_hw3.data.ResponseBookData
import com.example.sopt_seminar3_hw3.network.RequestToServerBook
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    lateinit var bookAdapter: BookAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        //getHashKey()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchbar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(searchtext: Editable?) {

            }

            override fun beforeTextChanged(searchtext: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(searchtext: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loadDatas(searchtext.toString())
            }
        })
    }

    fun loadDatas(searchtext: String) {
        val requestToServerBook = RequestToServerBook   // 도서 싱글톤 가져옴
        // 서버 요청
        requestToServerBook.bookservice.requestBook(searchtext)
            .enqueue(object : Callback<ResponseBookData> { // Callback 등록, Retrofit의 Callback을 import
                override fun onFailure(call: Call<ResponseBookData>, t: Throwable) {
                    // 통신 실패
                    Log.d("통신 실패", t.message)
                }

                override fun onResponse(
                    call: Call<ResponseBookData>,
                    response: Response<ResponseBookData>
                ) {
                    // 통신 성공
                    if (response.isSuccessful) {  // statusCode가 200-300 사이일 때, 응답 body 이용 가능
                        Log.d("성공", "${response.body()}")

                        bookAdapter = BookAdapter(applicationContext, response!!.body()!!.documents)
                        bookAdapter.notifyDataSetChanged()
                        rv_book.adapter = bookAdapter
                        rv_book.addItemDecoration(ItemDecoration())

                    } else {
                        Log.d("에러", "${response.body()}")
                    }
                }
            })



//    fun getHashKey() {
//        var packageInfo: PackageInfo? = null
//        try {
//            packageInfo =
//                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
//        for (signature in packageInfo!!.signatures) {
//            try {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            } catch (e: NoSuchAlgorithmException) {
//                Log.e(
//                    "KeyHash",
//                    "Unable to get MessageDigest. signature=$signature", e
//                )
//            }
//        }
//    }

    }
}
