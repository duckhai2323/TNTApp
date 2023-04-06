package com.example.myapp1.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp1.R
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.adapter.ViewItemAdapter
import com.example.myapp1.home.adapter.ViewItemAdapter0
import com.example.myapp1.home.adapter.ViewPageAdapter
import com.example.myapp1.home.adapter.ViewProductAdapter
import me.relex.circleindicator.CircleIndicator3
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        //DisplaySlider()
        val viewPage2:ViewPager2 = view.findViewById(R.id.viewpage2)
        val circleindicator:CircleIndicator3 = view.findViewById(R.id.circledicator1)
        val listItem:MutableList<Int> = mutableListOf()
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        val adapter_ = ViewPageAdapter(listItem)
        viewPage2.adapter = adapter_
        circleindicator.setViewPager(viewPage2)

        //DisplaySuggest0()
        val rvGoiYChuDe:RecyclerView = view.findViewById(R.id.rvGoiYChuDe)
        var listItemGoiY0:MutableList<ItemImageText> = mutableListOf()
        listItemGoiY0.add(ItemImageText(R.drawable.chude1,"Thời Trang"))
        listItemGoiY0.add(ItemImageText(R.drawable.chude2,"Điện Thoại"))
        listItemGoiY0.add(ItemImageText(R.drawable.chude3,"Điện Tử"))
        listItemGoiY0.add(ItemImageText(R.drawable.chude2,"Đồ Chơi"))
        listItemGoiY0.add(ItemImageText(R.drawable.chude1,"Thời Trang"))
        listItemGoiY0.add(ItemImageText(R.drawable.chude3,"Điện Tử"))
        rvGoiYChuDe.adapter = ViewItemAdapter0(listItemGoiY0)
        rvGoiYChuDe.layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        //DisplaySuggest1()
        val rvGoiY:RecyclerView = view.findViewById(R.id.rvGoiY)
        var listItemGoiY:MutableList<ItemGoiY> = mutableListOf()
        listItemGoiY.add(ItemGoiY(R.drawable.goi4,"đ 333.000","ĐANG BÁN CHẠY"))
        listItemGoiY.add(ItemGoiY(R.drawable.goi1,"đ 363.000","ĐÃ BÁN 11"))
        listItemGoiY.add(ItemGoiY(R.drawable.goi2,"đ 653.000","ĐÃ BÁN 23"))
        listItemGoiY.add(ItemGoiY(R.drawable.goi5,"đ 130.000","ĐANG BÁN CHẠY"))
        listItemGoiY.add(ItemGoiY(R.drawable.goi3,"đ 433.000","ĐÃ BÁN 18"))
        listItemGoiY.add(ItemGoiY(R.drawable.goi1,"đ 833.000","ĐANG BÁN CHẠY"))
        rvGoiY.adapter = ViewItemAdapter(listItemGoiY)
        rvGoiY.layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        //DisplayProduct1()
        val rvProduct:RecyclerView = view.findViewById(R.id.rvProduct)
        val listProduct1:MutableList<ItemProduct> = mutableListOf()
        listProduct1.add((ItemProduct(R.drawable.product4,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product2,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product3,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product4,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product5,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product2,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))

        rvProduct.adapter = ViewProductAdapter(listProduct1,object : ClickInterface {
            override fun setOnClick(pos: Int) {
                val intent1 = Intent(requireContext(),DetailActivity::class.java)
                startActivity(intent1)
            }
        })
        rvProduct.layoutManager = GridLayoutManager(
            context,2
        )
        return view
    }
}