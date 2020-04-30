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
 안드로이드에서 기본적으로 제공하는 SharedPreferernces는 db를 사용하지 않고도 데이터를 타입 등에 따라 관리하고, 파일로 저장한다. SharedPreferernces를 사용하면 액티비티 간 데이터를 주고받을 수 있다. (Key, Value) 형태로 사용한다.   

 1. 계정정보 저장   
 - LoginActivity
 
 ```kotlin
val pref = getSharedPreferences("USER", Context.MODE_PRIVATE)
val editor = pref.edit()

editor.putString("id", data?.getStringExtra("id"))
editor.putString("pw", data?.getStringExtra("pw"))
editor.commit()
```
오버라이드 한 onActivityResult() 안에서 자동로그인을 위하여 사용자가 입력한 계정정보를 저장한다.   
Context를 통해 접근하고, "USER"이라는 이름으로 파일이 저장되며 Editor를 이용해 값을 지정한다.   
get 메서드를 제외한 Data 저장(put), 삭제(remove, clear) 등을 할 경우에는 commit()을 꼭 호출해 주어야 한다.   


```kotlin
val loginpref = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE)
if(loginpref.getBoolean("AUTO_LOGIN", false)){
    startActivity(Intent(this, MainActivity::class.java))
```
    
onCreate()에서 종료하고 다시 접속해도 로그인 유지할 수 있도록 getSharedPreferences를 이용한다.   
첫번째 매개변수에는 데이터 저장 파일명, 두번째 매개변수에는 파일의 모드가 들어간다.(현재는 MODE_PRIVATE로, 실행한 앱에서만 데이터에 접근할 수 있게 설정하였다.)


2. 로그인 상태 저장   
- MainActivity
```kotlin

val loginpref = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE)
val editor = loginpref.edit()
editor.putBoolean("AUTO_LOGIN", true)
editor.apply()
```

   
로그인을 하여 MainActivity가 실행되면 ISLOGIN이라는 파일 안에 사용자의 로그인상태가 저장이 된다. AUTO_LOGIN이 true라면 로그인 된 상태로, 종료하고 다시 시작해도 바로 MainActivity가 실행될 수 있도록 한다.   

로그인 유무 판별은 LoginActivity에 구현한 코드를 통해 이루어진다.  
- LoginActivity

```kotlin        
val loginpref = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE)
if(loginpref.getBoolean("AUTO_LOGIN", false)){
    startActivity(Intent(this, MainActivity::class.java))
```
종료 시 다시 접속해도 로그인 유지될 수 있도록 한다.
