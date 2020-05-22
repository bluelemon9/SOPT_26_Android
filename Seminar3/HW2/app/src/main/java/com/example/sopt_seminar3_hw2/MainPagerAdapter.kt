package com.example.sopt_seminar3_hw2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sopt_seminar3_hw2.heart.HeartFragment
import com.example.sopt_seminar3_hw2.home.HomeFragment
import com.example.sopt_seminar3_hw2.mypage.MypageFragment
import com.example.sopt_seminar3_hw2.plus.PlusFragment
import com.example.sopt_seminar3_hw2.search.SearchFragment


class MainPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> PlusFragment()
            3 -> HeartFragment()
            else -> MypageFragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }

}