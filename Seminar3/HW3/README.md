# [성장과제 1] 카카오 OPEN API를 이용해 책 리스트 불러오기
### 검색어 입력하면 해당 리스트를 받아 와서 리사이클러뷰에 띄워준다   

![Alt Text](https://i.imgflip.com/446hi1.gif)

카카오 api 주소   
https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-book

<br>

<순서>   
[1] 리사이클러뷰 만들기   
[2] Retrofit을 이용하여 서버 통신

<br>   

# [1] 리사이클러뷰   

## 1. 반복될 뷰 하나 만들기   
- item_book.xml

## 2. 배치 방향 정하기   
- activity_main.xml   
```kotlin
    android:orientation="vertical"
    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
```

## 3. ViewHolder 만들기   
- BookViewHolder.kt   
```kotlin
class BookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
    val tv_contents = itemView.findViewById<TextView>(R.id.tv_contents)
    val tv_authors = itemView.findViewById<TextView>(R.id.tv_authors)
    val img_thumbnail = itemView.findViewById<ImageView>(R.id.img_thumbnail)

    fun bind(bookData : DocumentsData) {
        tv_title.text = bookData.title
        tv_contents.text = bookData.contents
        tv_authors.text = "저자: " + bookData.authors[0]
        Glide.with(itemView).load(bookData.thumbnail).into(img_thumbnail)
    }
}
```
사용할 데이터는 서버 통신에서 이용하는 DocumentsData이다. bind시 주의   

## 4. ViewAdapter 만들기   
- BookAdapter
```kotlin
class BookAdapter(private val context: Context, var datas: List<DocumentsData>) : RecyclerView.Adapter<BookViewHolder>() {
//class BookAdapter(private val context: Context) : RecyclerView.Adapter<BookViewHolder>() {
    //var datas = mutableListOf<DocumentsData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_kakaobook, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}
```
BookAdapter에 var datas: List<DocumentsData> 을 추가해줘야 한다!!

<br>

# [2] 서버통신

## 1. 라이브러리 추가   
- build.gradle에 추가   

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

- AndroidManifest.xml에 인터넷 권한 추가
```kotlin
    <uses-permission android:name="android.permission.INTERNET" />
    <application android:usesCleartextTraffic="true"
```


## 2. API문서 보고 Request / Response 객체 설계   
- ResponseBookData.kt   
```kotlin
data class ResponseBookData(
    val metas : List<MetaData>,
    val documents : List<DocumentsData>
)

data class MetaData(
    val total_count : Int,
    val pageable_count : Int,
    val is_end : Boolean
)

data class DocumentsData(
    val title : String,
    val contents : String,
//    val url : String,
//    val isbn : String,
    val authors : Array<String>,
//    val publisher : String,
//    val translators : Array<String>,
//    val price : Integer,
//    val sale_price :Integer,
    val thumbnail : String
//    val authors : String
//    val status : String
)
```
api 문서를 보고 작성한다. 그런데 문서상에선 meta로 해야할 것 같은데...metas로 해야 오류가 안난다...이유는..?   
List<DocumentsData> 형식에 주의
만약, 데이터 정의할 때 List<>로 해주지 않으면 실행시 객체/배열 오류가 나므로 타입에 주의   
data 패키지 안으로 정리



## 3. Retrofit Interface 설계
- RequestInterfaceBook.kt   
```kotlin
import com.example.sopt_seminar3_hw3.data.ResponseBookData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RequestInterfaceBook {
    @Headers("Authorization: KakaoAK 8132143585be1dcb6c82b159286694fb")
    @GET("/v3/search/book")

    fun requestBook(@Query("query") bookTitle : String) : Call<ResponseBookData>
}
```
@headers로 키값 넣어주고 @query로 검색할 책 제목 넣을 수 있게 함   
키값은 카카오 페이지 참고해서 4번째에 있는 모든 곳에 사용할 수 있는 키를 적용한다.



## 4. Retrofit Interface 실제 구현체 만들기   
- RequestToServerBook.kt
```kotlin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServerBook {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val bookservice : RequestInterfaceBook = retrofit.create(RequestInterfaceBook::class.java)
}
```
network 패키지 안으로 정리



## 5. Callback 등록하며 통신 요청   
- MainActivity.kt   
```kotlin
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
```
어댑터에서 var datas: List<DocumentsData> 라고 넣었으므로   
bookAdapter = BookAdapter(applicationContext, response!!.body()!!.documents) 이렇게 써야 한다!!
만약 어댑터에서 저렇게 2개로 해주지 않으면 데이터를 제대로 불러올 수 없다.   

<br>

### 책 제목 검색한 것에 따라 결과 다르게 불러오기
```kotlin
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
```
MainActivity.kt에서 edittext의 addTextChangedListener을 이용하여 텍스트 변화를 감지한다.   
변화가 감지될 때마다 loadDatas(searchtext.toString())를 통해 서버와 통신을 하게 된다.
searchtext는 서버 통신 때 RequestInterfaceBook.kt에서 @query 이 부분과 관련이 있다.

<br>

### 참고) 카카오 api 해시값 알아내는 법
```kotlin
    fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e(
                    "KeyHash",
                    "Unable to get MessageDigest. signature=$signature", e
                )
            }
        }
    }
```

<br>

### <MainActivity.kt 전체적으로 보기>
```kotlin
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
```



