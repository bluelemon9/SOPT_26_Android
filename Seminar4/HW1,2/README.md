# [성장과제 1]  확장함수 및 라이브템플릿 만들기

## :one: 라이브템플릿 추가   

### 1. 각종 라이브러리 (customLibrary)
Define -> Groovy 선택

<br>

### 2. 리사이클러뷰
#### 1) ViewHolder (customViewHolder)
- 제일 위에 변수부분 class InstaViewHolder 이부분을 지우고 $customViewHolder$ 로 변경

- val tv_username: ~~~ 과 같은 안에 들어가는 내용들은 다 지워주고 큰 뼈대만 남김
- fun bind(instaData: InstaData)  ->  fun bind(myData: $DataClass$) 로 변경   

Kotlin으로 저장

#### 2) ViewAdapter (customViewAdapter)
- class InstaAdapter  ->  class $customAdapter$ 로 변경
- InstaViewHolder  ->  $customViewHolder$ 로 변경
- InstaData  -> $DataClass$
- item_insta -> $customItemLayout$

Kotlin으로 저장

<br>

## :two: 확장함수  
new - Kotlin file 생성

### 1. 토스트 함수
- showToast.kt   
<초기>
```kotlin
fun Context.showToast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
```

<커스텀 토스트>
```kotlin
fun Activity.showToast(msg : String){
    val inflater : LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(R.layout.custom_toast, this.findViewById(R.id.toast_container))

    Toast(this).apply{
        layout.findViewById<TextView>(R.id.tv_toast_msg).text = msg
        view = layout
        duration = Toast.LENGTH_SHORT
        setGravity(Gravity.BOTTOM,0,100)
        show()
    }
}
```

<br>

### 2. 텍스트 변화 감지 함수
- textChangedListener.kt
```kotlin
fun EditText.textChangedListener(textChanged : (CharSequence?) -> Unit){
    this.addTextChangedListener(object : TextWatcher{
        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // s에 대한 정보를 넘겨줘야 함
            textChanged(s)
        }
    })
}
```

<br>

### 3. Retrofit 통신 enqueue   
```kotlin
fun<ResponseType> Call<ResponseType>.customEnqueue(
    onFail : () -> Unit = { Log.d("network", "통신에 실패했습니다.")},
    onSuccess : (ResponseType) -> Unit,
    onError : () -> Unit
    ){
    this.enqueue(object : Callback<ResponseType>{
        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            onFail()
        }

        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            response.body()?.let{   // body가 있다면 statusCode가 200-300 사이임
                onSuccess(it)   // 통신 결과를 전달해줌
            } ?: onError()  // response.body()가 null값 -> statusCode가 200-300이 아닌 경우 onError()를 호출
        }
    })
}
```

<br>

# [성장과제 2] 제플린 참고하면서 로그인, 회원가입 부분 완성

![Alt Text](https://i.imgflip.com/44wajp.gif)
- 아이디, 비밀번호 빈칸이라면 빨간 점 띄우기   
- 로그인버튼 클릭 시 빈칸이 있다면 하단 커스텀 토스트 띄우기      
- 로그인 화면에서 뒤로가기 버튼 누르면 종료 다이얼로그창 띄우기    

<br>

### 1. 아이디, 비밀번호 빈칸이면 빨간 점   
1) activity_login에서 발간 점 이미지 android:visibility="invisible" 로 설정   
2) LoginActivity에서 변경   
```kotlin
 et_id.textChangedListener {s ->
            if(s.isNullOrBlank()){ //it을 원하는 이름으로 명시 가능(s로 해보자)
                nullcheck1.visibility = VISIBLE   // 빈칸이면 빨간 동그라미 표시
                showToast(("아이디가 빈칸이네요"))
            }
        }

        btn_login.setOnClickListener{
            if(et_id.text.isNullOrBlank() || et_pwd.text.isNullOrBlank()){
                //Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                showToast("아이디와 비밀번호를 입력하세요!!")     // 4차 세미나 확장함수 사용
                nullcheck1.visibility = VISIBLE
                nullcheck2.visibility = VISIBLE
            }
```

<br>

### 2. 커스텀 토스트 만들기
#### 1. Vector Asset으로 ic_circle 만들기   
- fillColor 색 바꾸기

#### 2. drawable에서 우클릭 - Drawable Recource File로 바탕 까만색(bg_round) 만들기
```kotlin
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape = "rectangle">
    <solid android:color="#404040"/>
    <corners android:radius="22dp"/>
</shape>
```

<br>

#### 3. layout에서 우클릭 - Layout Resource File로 토스트화면 만들기(custom_toast)

<br>

#### 4. showToast.kt작성   
```kotlin
fun Activity.showToast(msg : String){
    val inflater : LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(R.layout.custom_toast, this.findViewById(R.id.toast_container))

    Toast(this).apply{
        layout.findViewById<TextView>(R.id.tv_toast_msg).text = msg
        view = layout
        duration = Toast.LENGTH_SHORT
        setGravity(Gravity.BOTTOM,0,100)
        show()
    }
}
```

#### 5. 작성한 내용 라이브템플릿으로 저장
- setting - Live Templates - +버튼 - myCustomToast라고 이름 저장 - 내용 복붙 - 하단에 Define은 kotlin 선택 - 완료   

<br>

### 3. 종료 다이얼로그   
1) custom_dialog.xml 생성   
2) LoginActivity.kt
```kotlin
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
```

```kotlin
// 다이얼로그 기본형
    override fun onBackPressed() {
        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        val alBuilder = AlertDialog.Builder(this)
        alBuilder.setTitle("프로그램 종료")
        alBuilder.setMessage("종료하시겠습니까?")

        alBuilder.setPositiveButton("예"){dialog, which ->
            finish()
        }
        alBuilder.setNegativeButton("아니오"){dialog, which ->
            return@setNegativeButton
        }

        alBuilder.show() // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.
    }
```

다이얼로그 창 크기 조절해보려고 했는데 잘 안된다,,,   
그리고 기본형에서는 아니요 버튼 누르면 다이얼로그창이 그냥 없어지는데 커스텀에서는 창이 안 없어지고 여백부분을 눌러야 창이 없어지는 오류,,,   


<br>

## [성장과제3] 클론앱   
https://github.com/Android-Clone-App-Instagram/InstagramCloneApp_Hoejin
