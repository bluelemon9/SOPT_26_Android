package com.example.sopt_seminar2_hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //리사이클러뷰 적용
    lateinit var webtoonAdapter: WebtoonAdapter
    val datas = mutableListOf<WebtoonData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webtoonAdapter = WebtoonAdapter(this)
        rv_main.adapter = webtoonAdapter    // 리사이클러뷰의 어댑터를 webtoonAdapter로 지정
        val myLayoutManager = GridLayoutManager(this, 3)
        rv_main.layoutManager = myLayoutManager
        loadDatas()         // 데이터 생성하고 어댑터에 전달
    }


    private fun loadDatas(){
        datas.apply {
            add(
                WebtoonData(
                    webtoonName = "연이힉명",
                    img_contents = "https://cdn.pixabay.com/photo/2013/07/12/18/20/santa-claus-153309__340.png",
                    rate = "9.88"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "즘비딸",
                    img_contents = "https://cdn.pixabay.com/photo/2017/11/06/18/30/eggplant-2924511__340.png",
                    rate = "9.97"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "더 북서",
                    img_contents = "https://cdn.pixabay.com/photo/2017/05/16/10/10/shark-2317422__340.png",
                    rate = "9.94"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "긴 띠리지는 등거",
                    img_contents = "https://cdn.pixabay.com/photo/2013/07/12/17/39/rat-152162__340.png",
                    rate = "9.92"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "이드나?",
                    img_contents = "https://cdn.pixabay.com/photo/2016/12/13/21/20/alien-1905155__340.png",
                    rate = "9.92"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "블랙 블러드",
                    img_contents = "https://cdn.pixabay.com/photo/2013/07/13/01/24/bunny-155674__340.png",
                    rate = "9.97"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "게벡",
                    img_contents = "https://cdn.pixabay.com/photo/2017/03/07/06/47/pirate-2123313__340.png",
                    rate = "9.98"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "츼강즌슬 긍해효",
                    img_contents = "https://cdn.pixabay.com/photo/2016/03/29/20/33/easter-1289267__340.png",
                    rate = "9.65"
                )
            )
            add(
                WebtoonData(
                    webtoonName = "거터부터터",
                    img_contents = "https://cdn.pixabay.com/photo/2016/04/01/09/29/cartoon-1299393__340.png",
                    rate = "9.94"
                )
            )
        }
        webtoonAdapter.datas = datas
        webtoonAdapter.notifyDataSetChanged()
    }
}
