package com.example.sopt_0425


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var instaAdapter: InstaAdapter
    val datas : MutableList<InstaData> = mutableListOf<InstaData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instaAdapter = InstaAdapter(view.context)
        rv_home.adapter = instaAdapter  // 리사이클러뷰의 어댑터를 instaAdapter로 지정해줌
        loadDatas() // 데이터를 임의로 생성하고 어댑터에 전달
    }

    private fun loadDatas(){
        datas.apply {
            add(
                InstaData(
                    userName = "김회진",
                    img_profile = "https://cdn.pixabay.com/photo/2017/05/26/07/17/jaguar-2345391__340.jpg",
                    img_contents = "https://cdn.pixabay.com/photo/2018/08/14/13/23/ocean-3605547__340.jpg"
                )
            )
            add(
                InstaData(
                    userName = "안드로이드",
                    img_profile = "https://cdn.pixabay.com/photo/2017/01/14/12/59/iceland-1979445__340.jpg",
                    img_contents = "https://cdn.pixabay.com/photo/2017/01/20/00/30/maldives-1993704__340.jpg"
                )
            )
            add(
                InstaData(
                    userName = "YB",
                    img_profile = "https://cdn.pixabay.com/photo/2014/11/30/14/11/kitty-551554__340.jpg",
                    img_contents = "https://cdn.pixabay.com/photo/2015/10/30/20/13/sunrise-1014712__340.jpg"
                )
            )
        }
        instaAdapter.datas = datas
        instaAdapter.notifyDataSetChanged()
    }
}
