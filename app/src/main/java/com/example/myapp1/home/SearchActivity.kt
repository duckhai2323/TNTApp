package com.example.myapp1.home

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import com.example.myapp1.ViewItemProduct2Adapter
import com.example.myapp1.detail.DetailActivity

class SearchActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val rvProduct2:RecyclerView = findViewById(R.id.rvProduct2)
        var listProduct1:ArrayList<ItemProduct> = ArrayList()
        listProduct1 = intent.getStringArrayExtra("productList") as ArrayList<ItemProduct>

        rvProduct2.adapter = ViewItemProduct2Adapter(listProduct1,object : ClickInterface {
            override fun setOnClick(pos: Int) {
                val intent1 = Intent(this@SearchActivity, DetailActivity::class.java)
                startActivity(intent1)
            }
        })
        rvProduct2.layoutManager = GridLayoutManager(
            this,2
        )
    }
}