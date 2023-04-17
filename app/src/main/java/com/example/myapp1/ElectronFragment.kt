package com.example.myapp1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.example.myapp1.home.ItemProduct
import me.relex.circleindicator.CircleIndicator3

val TagElectron:String = ElectronicFragment::class.java.name
class ElectronicFragment : Fragment(){
    lateinit var txtCity:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View =  inflater.inflate(R.layout.fragment_electronic, container, false)
        var backFragmentHom:ImageView = view.findViewById(R.id.backFragmentHome)

        //selectCity
        var selectCity:LinearLayout = view.findViewById(R.id.selectCity)
        txtCity = view.findViewById(R.id.city)
        selectCity.setOnClickListener {
            var dialogSelectCity:DialogSelectCity = DialogSelectCity()
            dialogSelectCity.show(requireFragmentManager(),"aaa")
        }


        //DisplaySlider()
        val viewPage2: ViewPager2 = view.findViewById(R.id.viewpage2)
        val circleindicator: CircleIndicator3 = view.findViewById(R.id.circledicator1)
        val listItem:MutableList<Int> = mutableListOf()
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        val adapter_ = ViewPageElecAdapter(listItem)
        viewPage2.adapter = adapter_
        circleindicator.setViewPager(viewPage2)

        //DisplaySuggest0
        var rvGoiYChuDe: RecyclerView = view.findViewById(R.id.rvGoiYElec)
        var listItemGoiY0:MutableList<ItemImageText> = mutableListOf()
        listItemGoiY0.add(ItemImageText(R.drawable.dienthoai,"Điện thoại"))
        listItemGoiY0.add(ItemImageText(R.drawable.maytinhbang,"Máy tính bảng"))
        listItemGoiY0.add(ItemImageText(R.drawable.laptop,"Laptop"))
        listItemGoiY0.add(ItemImageText(R.drawable.maytinh,"Máy tính để bàn"))
        listItemGoiY0.add(ItemImageText(R.drawable.loa,"Loa"))
        listItemGoiY0.add(ItemImageText(R.drawable.tivi,"Tivi"))
        listItemGoiY0.add(ItemImageText(R.drawable.tainghe,"Tai nghe"))
        listItemGoiY0.add(ItemImageText(R.drawable.camera,"Máy ảnh"))
        listItemGoiY0.add(ItemImageText(R.drawable.chuot,"Phụ kiện máy tính"))
        listItemGoiY0.add(ItemImageText(R.drawable.linhkien,"Linh kiện máy tính"))
        rvGoiYChuDe.adapter = ViewItemElecAdapter(listItemGoiY0,object: ClickInterface {
            override fun setOnClick(pos: Int) {
                val intent1 = Intent(requireContext(), ProductActivity::class.java)
                val bundle = Bundle()
                bundle.putString("category","electron")
                bundle.putString("city",txtCity.text.toString())
                bundle.putString("product",listItemGoiY0[pos].topic)
                intent1.putExtras(bundle)
                startActivity(intent1)
            }
        })
        rvGoiYChuDe.layoutManager= GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        //DisplaySuggest1
        var rvGoiYElec1:RecyclerView = view.findViewById(R.id.rvGoiYElec1)
        var listGoiYElec1:MutableList<ItemProduct> = mutableListOf()
        listGoiYElec1.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec1.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec1.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec1.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec1.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))

        rvGoiYElec1.adapter = ViewItemProdcut1Adapter(listGoiYElec1,object:ClickInterface{
            override fun setOnClick(pos: Int) {

            }
        })
        rvGoiYElec1.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        var LLDanhChoBan:LinearLayout = view.findViewById(R.id.LLDanhChoBan)
        LLDanhChoBan.setBackgroundResource(R.drawable.background_home2)
        var txtDanhChoBan:TextView = view.findViewById(R.id.txtDanhChoBan)
        txtDanhChoBan.setTextColor(ContextCompat.getColor(requireContext(),R.color.textColor))
        var txtMoiNhat:TextView = view.findViewById(R.id.txtMoiNhat)
        var LLMoiNhat:LinearLayout = view.findViewById(R.id.LLMoiNhat)

        LLDanhChoBan.setOnClickListener {
            LLDanhChoBan.setBackgroundResource(R.drawable.background_home2)
            LLMoiNhat.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            txtDanhChoBan.setTextColor(ContextCompat.getColor(requireContext(),R.color.textColor))
            txtMoiNhat.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
        }

        LLMoiNhat.setOnClickListener {
            LLMoiNhat.setBackgroundResource(R.drawable.background_home2)
            LLDanhChoBan.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            txtDanhChoBan.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            txtMoiNhat.setTextColor(ContextCompat.getColor(requireContext(),R.color.textColor))
        }

        var rvGoiYElec2:RecyclerView = view.findViewById(R.id.rvGoiYElec2)
        var listGoiYElec2:MutableList<ItemProduct> = mutableListOf()
        listGoiYElec2.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec2.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec2.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec2.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec2.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listGoiYElec2.add(ItemProduct(resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))

        rvGoiYElec2.adapter = ViewItemProduct2Adapter(listGoiYElec2,object:ClickInterface{
            override fun setOnClick(pos: Int) {

            }
        })
        rvGoiYElec2.layoutManager = GridLayoutManager(
            context,2
        )

        backFragmentHom.setOnClickListener{
            fragmentManager?.popBackStack()
        }
        return view
    }

    fun update(str:String,obj:String) {
        txtCity.text = str
    }
}