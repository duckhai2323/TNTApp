package com.example.myapp1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProductManagerFragment : Fragment(){
    lateinit var viewTabLayout:ViewPager2
    lateinit var viewItemManagerAdapter:ViewItemManagerAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_productmanager, container, false)
        var tabLayout:TabLayout = view.findViewById(R.id.tabLayoutManager)
        viewTabLayout = view.findViewById(R.id.viewPage2Manager)
        viewItemManagerAdapter = ViewItemManagerAdapter(context as FragmentActivity)
        viewTabLayout.adapter = viewItemManagerAdapter
        TabLayoutMediator(tabLayout, viewTabLayout) { tab, position ->
            when(position) {
                0->{
                    tab.text = "Đang hiển thị"
                }
                1->{
                    tab.text = "Chờ xác nhận"
                }
                2->{
                    tab.text="Đang giao"
                }
                3 -> {
                    tab.text = "Đã giao"
                }
                else->{
                    tab.text="Đang hiển thị"
                }
            }
        }.attach()
        return view
    }
}