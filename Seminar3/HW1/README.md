# <Retrofit을 활용한 회원가입 통신>

## 0. 서버와 필요한 데이터 논의
  -> 아이디, 비밀번호, 이름, 이메일, 전화번호를 서버에 전달하고 응답 받기.

## 1. 라이브러리 추가
- build.gradle (app)
```kotlin
//Retrofit 라이브러리 : https://github.com/square/retrofit
implementation 'com.squareup.retrofit2:retrofit:2.6.2'

//Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위해
implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'

//객체 시리얼라이즈를 위한 Gson 라이브러리 : https://github.com/google/gson
implementation'com.google.code.gson:gson:2.8.6'

//Retrofit 에서 Gson 을 사용하기 위한 라이브러리
implementation'com.squareup.retrofit2:converter-gson:2.6.2'
```

- AndroidManifest.xml
```kotlin
<uses-permission android:name="android.permission.INTERNET" />
<application android:usesCleartextTraffic="true"
```

<br>

## 2. API문서 보고 Request / Response 객체 설계
- RequesetJoin
```
data class RequestJoin (
    val id : String,
    val password : String,
    val name : String,
    val email : String,
    val phone : String
)
```

- ResponseJoin
```kotlin
data class ResponseJoin(
    val status: Int,
    val success : Boolean,
    val message : String
)
```

<br>

## 3. Retrofit Interface 설계
- RequestJoinInterface
```kotlin
import com.example.sopt_seminar1_hw2_hw4.data.RequestJoin
import com.example.sopt_seminar1_hw2_hw4.data.ResponseJoin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestJoinInterface {
    @POST("/user/signup")
    fun requestJoin(@Body body: RequestJoin): Call<ResponseJoin>
}
```

<br>

## 4. Retrofit Interface 실제 구현체 만들기
- RequestToServer
```kotlin
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RequestToServer {
    var retrofit = Retrofit.Builder()
        .baseUrl("http://13.209.144.115:3333")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service : RequestInterface = retrofit.create(RequestInterface::class.java)
    val joinservice : RequestJoinInterface = retrofit.create(RequestJoinInterface::class.java)
}
```

<br>

## 5. Callback 등록하며 통신 요청
- JoiActivity   

class 내부 override fun onCreate 위에 코드 추가(싱글톤 그대로 가져옴)
```kotlin
val requestToServer = RequestToServer
```
   

else 뒷부분 수정
```kotlin
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
                        showToast("통신 실패")
                    }

                    override fun onResponse(
                        call: Call<ResponseJoin>,
                        response: Response<ResponseJoin>
                    ) {
                        // 통신 성공
                        if(response.isSuccessful){  // statusCode가 200-300 사이일 때, 응답 body 이용 가능
                            if(response.body()!!.success){  // ResponseJoin의 success가 true인 경우 -> 로그인
                                //Toast.makeText(this@LoginActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                showToast("회원가입 성공")     // 4차 세미나 확장함수 사용
                                val intent = Intent(this@JoinActivity, MainActivity::class.java)
                                intent.putExtra("id", et_joinid.text.toString())
                                intent.putExtra("pw", et_joinpwd.text.toString())
                                startActivity(intent)
                                finish()
                            } else{
                                //Toast.makeText(this@LoginActivity, "다시 한번 확인하세요!", Toast.LENGTH_SHORT).show()
                                showToast("다시 한번 확인하세요!")       // 4차 세미나 확장함수 사용
                            }
                        }
                    }
                })
//                val intent = Intent(this, LoginActivity::class.java)
//                intent.putExtra("id", et_joinid.text.toString())
//                intent.putExtra("pw", et_joinpwd.text.toString())
//                setResult(Activity.RESULT_OK, intent)
//                finish()
            }
```
