package com.example.myapp1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BillActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        var viewTabLayout = findViewById<ViewPager2>(R.id.viewTabLayout)

        viewTabLayout.adapter = ViewTabItemAdpter(supportFragmentManager,lifecycle)
        TabLayoutMediator(tabLayout, viewTabLayout) { tab, position ->
            when(position) {
                0->{
                    tab.text = "Chờ xác nhận"
                }
                1->{
                    tab.text = "Đang giao"
                }
                2->{
                    tab.text="Đã giao"
                }
                else -> {
                    tab.text = "Chờ xác nhận"
                }
            }
        }.attach()

        var back = findViewById<ImageView>(R.id.backFromBill)
        back.setOnClickListener{
            onBackPressed()
        }
    }
}