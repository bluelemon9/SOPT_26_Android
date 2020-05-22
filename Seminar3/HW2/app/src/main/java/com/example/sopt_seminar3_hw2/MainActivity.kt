package com.example.sopt_seminar3_hw2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginpref = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE)
        val editor = loginpref.edit()
        editor.putBoolean("AUTO_LOGIN", true)
        editor.apply()

        // 로그아웃
        btn_logout.setOnClickListener {
            editor.clear()
            editor.commit()

            Toast.makeText(applicationContext, "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        main_viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        main_viewPager.offscreenPageLimit = 4
        main_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // 네비게이션 메뉴 아이템 체크
                bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> main_viewPager.currentItem = 0
                R.id.menu_search -> main_viewPager.currentItem = 1
                R.id.menu_plus -> main_viewPager.currentItem = 2
                R.id.menu_heart -> main_viewPager.currentItem = 3
                R.id.menu_person -> main_viewPager.currentItem = 4
            }
            true
        }
    }
}
