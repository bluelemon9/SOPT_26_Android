# [성장과제1] 네이버 웹툰 뷰 만들기
## Grid RecyclerView

![Alt Text](https://i.imgflip.com/40dgfg.gif)

  

### 1. 반복될 뷰 하나 만들기  
- item_webtoon.xml   


### 2. 배치 방향 정하기   
GridLayoutManager : 바둑판 형식 배치   
 
- activity_main.xml
```kotlin
app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
app:spanCount="3"
```
리사이클러뷰를 그리드 형태로 사용하기 위해 layoutManager을 GirdLayoutManager로 설정해준다.
spanCount는 한 줄에 넣을 수 있는 개수를 정해주는데 여기서는 3으로 설정했다.   


### 3. 어디에 데이터를 넣을지, 어떤 데이터를  설정  
#### 1) 데이터 형태 정함     
- WebtoonData.kt

```kotlin
data class WebtoonData(
    val webtoonName : String,
    val rate : String,
    val img_contents : String
)
```

#### 2) ViewHolder 만들기   
- WebtoonViewHolder.kt   

```kotlin
class WebtoonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val tv_webtoonname = itemView.findViewById<TextView>(R.id.tv_webtoonname)
    val tv_rate = itemView.findViewById<TextView>(R.id.tv_rate)
    val img_contents = itemView.findViewById<ImageView>(R.id.img_contents)

    fun bind(webtoonData: WebtoonData){
        tv_webtoonname.text = webtoonData.webtoonName
        tv_rate.text = webtoonData.rate
        Glide.with(itemView).load(webtoonData.img_contents).into(img_contents)
    }
}
```

#### 3) Adapter 만들기   
- WebtoonAdapter.
```kotlin
class WebtoonAdapter(private val context : Context) : RecyclerView.Adapter<WebtoonViewHolder>() {
    var datas = mutableListOf<WebtoonData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebtoonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_webtoon, parent, false)
        return WebtoonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: WebtoonViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}
```


#### 4) 연결   
Adapter를 통해 데이터 전달. ViewHolder를 이용해 받은 데이터를 뷰에 뿌려준다.   


- MainActivity   
리사이클러뷰 적용은 세미나 때와 동일하게 하면 된다.
```kotlin
webtoonAdapter = WebtoonAdapter(this)
rv_main.adapter = webtoonAdapter    // 리사이클러뷰의 어댑터를 webtoonAdapter로 지정
val myLayoutManager = GridLayoutManager(this, 3)
rv_main.layoutManager = myLayoutManager
loadDatas()         // 데이터 생성하고 어댑터에 전달
```
 다만, onCreate()에서 GirdLayoutManager을 설정해주어야 한다.   
 아래 loadDatas()은 과제1과 동일한 형식으로 작성하면 된다.
 
 
 
참고로...activity_main.xml에 백그라운드 색깔 gray로 주었다.



참고블로그   
https://blog.naver.com/PostView.nhn?blogId=rkswlrbduf&logNo=221200565254&redirect=Dlog&widgetTypeCall=true&directAccess=false



그리드리사이클러뷰를 사용해보는 것이 가장 큰 목표라 다른 부분을 많이 신경쓰지는 못함.   
추후 리드미 다시 정리해서 수정하기!!



<개선방향> 
1. 뷰 페이저 넣어서 옆으로 슬라이드 가능하게 하기
2. 바텀네비게이션 fragment 만들어서 하단 탭 클릭시 다른 화면이 뜰 수 있게 하기


--------추가사항------------   

3. 하단 아이콘이 4개 이상일 때 아이콘 이미지만 보인다. 아이콘 글자 같이 보이게 하는 법: shift mode를 false로 설정.

4. 그리드레이아웃 이미지 같은 크기로 보여지게 하기
https://webnautes.tistory.com/1299
그리고 이왕이면 itemDecoration 기능 넣어서 각 아이템 주위에 여백 주면 좋을 듯

5. 세부기능 넣기 (좀 더 웹툰창처럼...)
