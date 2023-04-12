package com.example.myapp1

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ItemProduct

class ClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        addEvent()
    }

    private fun addEvent() {
        DisplayBangTin()
        DisplayComplete()
    }

    private fun DisplayComplete() {
        var listTinCompelete:MutableList<String> = mutableListOf()
        var rvBanthanhcong = findViewById<RecyclerView>(R.id.rvBanthanhcong)

        listTinCompelete.add("Thanh lí tai nghe AirPod Pro chính hãng siêu xịn xò con chim nha quý zị")
        listTinCompelete.add("Thanh lí tai nghe AirPod Pro chính hãng siêu xịn xò con chim nha quý zị")
        listTinCompelete.add("Thanh lí tai nghe AirPod Pro chính hãng siêu xịn xò con chim nha quý zị")
        listTinCompelete.add("Thanh lí tai nghe AirPod Pro chính hãng siêu xịn xò con chim nha quý zị")
        listTinCompelete.add("Thanh lí tai nghe AirPod Pro chính hãng siêu xịn xò con chim nha quý zị")

        if(listTinCompelete.size > 3) {
            var listTinComplete1:MutableList<String> = mutableListOf()
            val xemtatca = findViewById<LinearLayout>(R.id.xemtatca1)
            xemtatca.visibility = View.VISIBLE
            listTinComplete1.add(listTinCompelete[0])
            listTinComplete1.add(listTinCompelete[1])
            listTinComplete1.add(listTinCompelete[2])
            rvBanthanhcong.adapter = ViewItemAdapterTin1(listTinComplete1)
        } else {
            rvBanthanhcong.adapter = ViewItemAdapterTin1(listTinCompelete)
        }
        rvBanthanhcong.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun DisplayBangTin() {
        var listTin:MutableList<ItemProduct> = mutableListOf()
        var rvDangsp = findViewById<RecyclerView>(R.id.rvDangsp)
        listTin.add(ItemProduct(R.drawable.product3,"Tai nghe chính hãng, hàng xịn AirPod Provip","259.000 đ","Đã sử dụng 1 năm"))
        listTin.add(ItemProduct(R.drawable.product4,"Tai nghe chính hãng, hàng xịn AirPod Provip","259.000 đ","Đã sử dụng 1 năm"))
        listTin.add(ItemProduct(R.drawable.product2,"Tai nghe chính hãng, hàng xịn AirPod Provip","259.000 đ","Đã sử dụng 1 năm"))
        listTin.add(ItemProduct(R.drawable.product4,"Tai nghe chính hãng, hàng xịn AirPod Provip","259.000 đ","Đã sử dụng 1 năm"))
        listTin.add(ItemProduct(R.drawable.product2,"Tai nghe chính hãng, hàng xịn AirPod Provip","259.000 đ","Đã sử dụng 1 năm"))

        if(listTin.size > 3) {
            var listTin1:MutableList<ItemProduct> = mutableListOf()
            val xemtatca = findViewById<LinearLayout>(R.id.xemtatca1)
            xemtatca.visibility = View.VISIBLE
            listTin1.add(listTin[0])
            listTin1.add(listTin[1])
            listTin1.add(listTin[2])
            rvDangsp.adapter = ViewItemAdapterTin(listTin1)
        } else {
            rvDangsp.adapter = ViewItemAdapterTin(listTin)
        }
        rvDangsp.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
}