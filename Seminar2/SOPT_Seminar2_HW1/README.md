# [과제1] Bottom Navigation, ViewPager, RecyclerView

![Alt Text](https://i.imgflip.com/3zf0gn.gif)


# [과제2] itemDecoration, clipToPadding
## 1. itemDecoration
: RecyclerView에서 각 아이템 주위에 여백을 주는 방법이다. ItemDecoration.kt를 생성하여 ItemDecoration을 설정하고 원하는 리사이클러뷰에 recyclerview이름.*addItemDecoration(ItemDecoration이름())*!코드를 추가한다.

- ItemDecoration.kt
```kotlin
package com.example.sopt_seminar2_hw2

import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View


public class ItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = 10
        outRect.right = 10
        outRect.top = 10
    }
}
```
위양옆에 10만큼의 공간을 주고자 한다.

- HomeFragment.kt
```kotlin
instaAdapter.datas = datas
instaAdapter.notifyDataSetChanged()
rv_home.addItemDecoration(ItemDecoration())
```
맨 마지막 줄에 추가해주었다.

![Alt Text](https://i.imgflip.com/3zh1qg.gif)   
위양옆에 일정한 크기의 여백이 생긴것을 확인할 수 있다!

## 2. clipToPadding
: RecyclerView를 이용하면서 다른 뷰와 구분하고 싶을 때  padding을 준다. 그런데 그렇게 하면 padding을 준 만큼 스크롤 영역이 작아지게 된다. 이 때, clipToPadding 속성값을 이용하면 스크롤 영역은 유지한 채 여백을 줄 수 있다.   

명확한 확인을 위해 먼저 recyclerview에 paddingTop = "10dp", paddingBotton = "20dp"를 주었다.   


1) clipToPadding의 값을 true로 설정했을 때

```kotlin
android:clipToPadding="false"
```

![Alt Text](https://i.imgflip.com/3zc6k4.gif)

상단에 10dp만큼의 패딩이 적용된 상태로 화면이 스크롤된다.   



2) clipToPadding의 값을 true로 설정했을 때

```kotlin
android:clipToPadding="true"
```

![Alt Text](https://i.imgflip.com/3zc6xl.gif)

스크롤 시 상단의 padding 영역까지 화면이 보여지게 된다.
