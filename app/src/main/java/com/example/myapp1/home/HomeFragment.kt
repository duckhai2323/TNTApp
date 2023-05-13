package com.example.myapp1.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp1.CartActivity
import com.example.myapp1.R
import com.example.myapp1.ViewItemProduct2Adapter
import com.example.myapp1.ViewPageElecAdapter
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.adapter.ViewItemAdapter
import com.example.myapp1.home.adapter.ViewItemAdapter0
import com.example.myapp1.home.adapter.ViewPageAdapter
import com.example.myapp1.home.adapter.ViewProductAdapter
import me.relex.circleindicator.CircleIndicator3
class HomeFragment : Fragment() {
    lateinit var mActivityHome:HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
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

        listItemGoiY0.add(ItemImageText(R.drawable.xetot1,"Xe cộ",""))
        listItemGoiY0.add(ItemImageText(R.drawable.tulanhtot1,"Điện lạnh",""))
        listItemGoiY0.add(ItemImageText(R.drawable.dienthoaitot1,"Đồ điện tử",""))
        listItemGoiY0.add(ItemImageText(R.drawable.thoitrangtot1,"Thời trang",""))
        listItemGoiY0.add(ItemImageText(R.drawable.noithattot1,"Nội thất",""))
        listItemGoiY0.add(ItemImageText(R.drawable.vanphongphamtot1,"Văn phòng phẩm",""))
        listItemGoiY0.add(ItemImageText(R.drawable.giaitritot1,"Giải trí, Thể thao, Sở thích",""))
        listItemGoiY0.add(ItemImageText(R.drawable.myphamtot1,"Mỹ phẩm",""))
        rvGoiYChuDe.adapter = ViewItemAdapter0(listItemGoiY0,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                mActivityHome.getFragment("electron")
            }
        })
        rvGoiYChuDe.layoutManager= GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        //DisplayProduct1()
        val rvProduct:RecyclerView = view.findViewById(R.id.rvProduct)
        val listProduct1:MutableList<ItemProduct> = mutableListOf()
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))

        rvProduct.adapter = ViewItemProduct2Adapter(listProduct1,object : ClickInterface {
            override fun setOnClick(pos: Int) {
                val intent1 = Intent(requireContext(),DetailActivity::class.java)
                startActivity(intent1)
            }
        })
        rvProduct.layoutManager = GridLayoutManager(
            context,2
        )

        var imgCart:ImageView = view.findViewById(R.id.imgCartHome)
        imgCart.setOnClickListener{
            val i = Intent(context,CartActivity::class.java)
            startActivity(i)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivityHome = requireActivity() as HomeActivity
    }
}