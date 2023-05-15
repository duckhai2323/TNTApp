package com.example.myapp1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.HomeActivity
import com.example.myapp1.home.ItemProduct
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchActivity : AppCompatActivity() {
    lateinit var key:String
    val db = Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val rvProduct2: RecyclerView = findViewById(R.id.rvListItem)
        key = intent.getStringExtra("KeyWord").toString().uppercase().replace(" ", "")
        val backButton: ImageView = findViewById(R.id.backFromSearch)
        var productList:MutableList<ItemProduct> = mutableListOf()
        db.collection("products")
            .addSnapshotListener { result, e ->
                productList.clear()
                for (document in result!!) {
                    val imageUrl = document.data?.get("picture") as MutableList<String>
                    val title = document.data?.get("title").toString()
                    val price = document.data?.get("price").toString()
                    var city  = document.data?.get("city").toString()
                    var  idProduct = document.data?.get("id").toString()
                    val timestamp = document.getTimestamp("timestamp")
                    val title_check = title.uppercase().replace(" ", "")
                    if (title_check.contains(key) && key != "") {
                        productList.add(ItemProduct(idProduct, imageUrl[0], title, price, city))
                    }
                }
                rvProduct2.adapter = ViewItemAdapterTin(productList,object : ClickInterface {
                    override fun setOnClick(pos: Int) {
                        val intent1 = Intent(this@SearchActivity, DetailActivity::class.java)
                        intent1.putExtra("id", productList[pos].id)
                        startActivity(intent1)
                    }
                })
                rvProduct2.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            }
        backButton.setOnClickListener {
            val intent2 = Intent(this@SearchActivity, HomeActivity::class.java)
            startActivity(intent2)
        }

    }
}