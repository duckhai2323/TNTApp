package com.example.myapp1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
var brand_:String?=null
class ProductActivity : AppCompatActivity(), OnInputData {
    lateinit var viewAdapter:ViewItemFilteAdapter
    lateinit var dialogFilte:DialogFilter
    var listFilte:MutableList<FilteItem> = mutableListOf()
    lateinit var address:TextView
    var position:Int = 0
    override fun sendData(str: String, key: String) {
        val item = FilteItem(
            str,
            R.drawable.outline_keyboard_arrow_down_24,
            R.drawable.background_button2_1,
            key
        )
        if (position == 1) {
            when (key) {
                "series" -> {
                    viewAdapter.updateData(item, position + 1)
                }
                "brand" -> {
                    brand_ = str
                    viewAdapter.updateData(item, position)
                }
            }
        } else viewAdapter.updateData(item, position)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        address = findViewById<TextView>(R.id.addressText)
        val i = intent
        val bundle = i.extras

        if(bundle!=null) {
            address.text = bundle.getString("city")
            when(bundle.getString("product")){
                "Điện thoại" -> {
                    listFilte.add(FilteItem("Điện thoại",R.drawable.outline_keyboard_arrow_down_24,R.drawable.background_button2_1,"product"))
                    listFilte.add(FilteItem("Hãng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"brand"))
                    listFilte.add(FilteItem("Dòng máy",R.drawable.baseline_add_24_1,R.drawable.background_filter,"series"))
                    listFilte.add(FilteItem("Giá",R.drawable.baseline_add_24_1,R.drawable.background_filter,"price"))
                    listFilte.add(FilteItem("Ship COD",R.drawable.baseline_add_24_1,R.drawable.background_filter,"shipcod"))
                    listFilte.add(FilteItem("Thanh toán đảm bảo",R.drawable.baseline_add_24_1,R.drawable.background_filter,"pay"))
                    listFilte.add(FilteItem("Tình trạng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"status"))
                    listFilte.add(FilteItem("Bảo hành",R.drawable.baseline_add_24_1,R.drawable.background_filter,"warranty"))
                    listFilte.add(FilteItem("Dung lượng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"capacity"))
                    listFilte.add(FilteItem("Màu sắc",R.drawable.baseline_add_24_1,R.drawable.background_filter,"color"))
                    listFilte.add(FilteItem("Thời gian sử dụng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"time"))
                }

                "Laptop" -> {
                    listFilte.add(FilteItem("Laptop",R.drawable.outline_keyboard_arrow_down_24,R.drawable.background_button2_1,"product"))
                    listFilte.add(FilteItem("Hãng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"brand"))
                    listFilte.add(FilteItem("Dòng máy",R.drawable.baseline_add_24_1,R.drawable.background_filter,"series"))
                    listFilte.add(FilteItem("Giá",R.drawable.baseline_add_24_1,R.drawable.background_filter,"price"))
                    listFilte.add(FilteItem("Ship COD",R.drawable.baseline_add_24_1,R.drawable.background_filter,"shipcod"))
                    listFilte.add(FilteItem("Thanh toán đảm bảo",R.drawable.baseline_add_24_1,R.drawable.background_filter,"pay"))
                    listFilte.add(FilteItem("Tình trạng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"status"))
                    listFilte.add(FilteItem("Bảo hành",R.drawable.baseline_add_24_1,R.drawable.background_filter,"warranty"))
                    listFilte.add(FilteItem("RAM",R.drawable.baseline_add_24_1,R.drawable.background_filter,"ram"))
                    listFilte.add(FilteItem("Ổ cứng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"c"))
                    listFilte.add(FilteItem("Card màn hình",R.drawable.baseline_add_24_1,R.drawable.background_filter,"card"))
                    listFilte.add(FilteItem("Màu sắc",R.drawable.baseline_add_24_1,R.drawable.background_filter,"color"))
                    listFilte.add(FilteItem("Thời gian sử dụng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"time"))
                }
            }
        }
        var rvList = findViewById<RecyclerView>(R.id.rvListFilte)

        viewAdapter = ViewItemFilteAdapter(listFilte,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                position = pos
                dialogFilte = DialogFilter(listFilte[pos].key)
                dialogFilte.show(supportFragmentManager,"aaa")
            }
        })
        rvList.adapter = viewAdapter
        rvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        var rvProductFilter:RecyclerView = findViewById<RecyclerView>(R.id.rvProductFilter)
        var listProductFilter:MutableList<ItemProduct> = mutableListOf()
        listProductFilter.add(ItemProduct(R.drawable.product2,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listProductFilter.add(ItemProduct(R.drawable.product3,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listProductFilter.add(ItemProduct(R.drawable.product4,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listProductFilter.add(ItemProduct(R.drawable.product5,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))
        listProductFilter.add(ItemProduct(R.drawable.product2,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k"))

        var check:Boolean = true
        rvProductFilter.adapter = ViewItemProduct2Adapter(listProductFilter,object:ClickInterface{
            override fun setOnClick(pos: Int) {

            }
        })
        rvProductFilter.layoutManager = GridLayoutManager(
            this,2
        )

        var imgAdapter:ImageView = findViewById<ImageView>(R.id.adapter)
        imgAdapter.setOnClickListener{
            if(check) {
                rvProductFilter.adapter = ViewItemAdapterTin(listProductFilter)
                rvProductFilter.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                check = false
            } else {
                rvProductFilter.adapter = ViewItemProduct2Adapter(listProductFilter,object:ClickInterface{
                    override fun setOnClick(pos: Int) {

                    }
                })
                rvProductFilter.layoutManager = GridLayoutManager(
                    this,2
                )
                check = true
            }
        }
    }
}