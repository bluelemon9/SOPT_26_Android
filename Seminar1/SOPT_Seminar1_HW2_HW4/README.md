# [과제2] 회원가입 및 로그인 기능 구현하기

- 회원가입 완료시 LoginActivity로 돌아오고, 회원가입 성공한 id와 pw가 입력되어 있도록 구현.

MainActivity: 로그인 완료 창   
LoginActivity: 로그인 창   
JoinActivity: 회원가입 창

JoinActivity에서 회원가입 한 정보(아이디와 비밀번호)가 LoginActivity로 돌아왔을 때 입력되어 있도록 해야 한다. 


## StartActivityForResult, intent.putExtra, getstringExtra

1. 화면 전환에 주로 사용하는 StartActivity() 대신 결과값을 반환할 수 있는 StartActivityForResult를 사용한다.
- LoginActivity

```kotlin
btn_join.setOnClickListener{
    val intent = Intent(this, JoinActivity::class.java)
    startActivityForResult(intent, REQUEST_CODE)
        }
```

- JoinActivity 

```kotlin
val intent = Intent(this, LoginActivity::class.java)

intent.putExtra("id", et_joinid.text.toString())
intent.putExtra("pw", et_joinpwd.text.toString())
setResult(Activity.RESULT_OK, intent)
finish()
```

intent를 통해 값을 전달할 수 있다. putExtra()의 첫번째 매개변수에는 값의 이름, 두번째 매개변수에는 값이 들어간다. setResult()를 통해 intent를 전달한다.   


2. putExtra()로 값을 전달했으니 이제 값을 받는 액티비티인 LoginActivity에서 onActivityResult()함수를 오바라이트하여 getStringExtra()로 값을 받아온다.
- LoginActivity

```kotlin
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                // 로그인 입력창에 회원가입 성공한 id와 pw가 입력되도록 함
                et_id.setText(data?.getStringExtra("id"))
                et_pwd.setText(data?.getStringExtra("pw"))
 ```
 
 
 # [성장과제2] 자동 로그인 구현하기
 - 회원가입 시 LoginActivity로 돌아와 가입한 id, pw로 자동 로그인하기.
 - 로그인 하여 MainActivity로 간 경우 앱을 종료했다가 다시 켜면 LoginActivity에서 자동 로그인 하기.
 
 ## SharedPreferences
 - LoginActivity
 
 ```kotlin
val pref = getSharedPreferences("USER", Context.MODE_PRIVATE)
val editor = pref.edit()

editor.putString("id", data?.getStringExtra("id"))
editor.putString("pw", data?.getStringExtra("pw"))
editor.commit()
```
// 자동로그인 -> 계정 정보 저장
// Context를 통해 접근하고, "USER"이라는 이름으로 파일이 저장되며 Editor를 이용해 값을 지정한다.

 
