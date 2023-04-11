package com.example.myapp1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface

class ProductActivity : AppCompatActivity(), OnInputData {
    lateinit var viewAdapter:ViewItemFilteAdapter
    lateinit var dialogFilte:DialogFilter
    var listFilte:MutableList<FilteItem> = mutableListOf()
    lateinit var address:TextView
    var positon:Int = 0
    override fun sendData(str: String, key: String) {
        val item = FilteItem(str,R.drawable.outline_keyboard_arrow_down_24,R.drawable.background_button2_1,key)
        viewAdapter.updateData(item,positon)
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
            }
        }
        var rvList = findViewById<RecyclerView>(R.id.rvListFilte)

        viewAdapter = ViewItemFilteAdapter(listFilte,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                positon = pos
                dialogFilte = DialogFilter(listFilte[pos].key)
                dialogFilte.show(supportFragmentManager,"aaa")
            }
        })
        rvList.adapter = viewAdapter
        rvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
    }
}