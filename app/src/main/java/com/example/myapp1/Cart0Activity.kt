package com.example.myapp1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.email
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Cart0Activity : AppCompatActivity() {
    private val db = Firebase.firestore
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart0)
        addEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent() {
        DisplayListCart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun DisplayListCart() {
        var rvListCart = findViewById<RecyclerView>(R.id.rvListCart)
        var listProduct:MutableList<ItemCart> = mutableListOf()
        var listId:MutableList<String> = mutableListOf()
        val ref = db.collection("products")
        db.collection("users").whereEqualTo("email", email)
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(document in it.documents) {
                        listId = document.data?.get("cart") as MutableList<String>
                    }
                    for(i in listId) {
                        ref.document(i).get().addOnSuccessListener { it1->
                            if(it1.exists()) {
                                val client = it1.data?.get("username").toString()
                                val imageUrl = it1.data?.get("picture") as MutableList<String>
                                var title = it1.data?.get("title").toString()
                                var price = it1.data?.get("price").toString()
                                var city  = it1.data?.get("city").toString()
                                var  idProduct =it1.data?.get("id").toString()
                                val timestamp = it1.getTimestamp("timestamp")
                                var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                                val txtTimeCount = mTimeCount?.timeCount()
                                city = "$city . $txtTimeCount"
                                listProduct.add(ItemCart(idProduct,client,imageUrl[0],title,price,city))
                            }
                            val adapter_ = ViewItemCartAdapter(listProduct,this)
                            rvListCart.adapter = adapter_
                            rvListCart.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                       }
                   }
                }
            }
    }
}