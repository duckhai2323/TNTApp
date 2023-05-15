package com.example.myapp1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
import com.example.myapp1.home.username
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDisplayFragment: Fragment() {
    private val db = Firebase.firestore
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_viewpageproduct, container, false)
        var rvProductDisplay:RecyclerView = view.findViewById(R.id.rvProductOrder)
        rvProductDisplay.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var listProduct:MutableList<ItemProduct> = mutableListOf()
        db.collection("products")
            .whereEqualTo("username", username)
            .whereEqualTo("display","true")
            .get()
            .addOnSuccessListener {it1->
                if(!it1.isEmpty) {
                    for(document in it1.documents) {
                        val imageUrl = document.data?.get("picture") as MutableList<String>
                        var title = document.data?.get("title").toString()
                        var price = document.data?.get("price").toString()
                        var city  = document.data?.get("city").toString()
                        var  idProduct = document.data?.get("id").toString()
                        val timestamp = document.getTimestamp("timestamp")
                        var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                        val txtTimeCount = mTimeCount?.timeCount()
                        city = "$city . $txtTimeCount"
                        listProduct.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                    }
                    rvProductDisplay.adapter = ViewItemAdapterTin(listProduct,object:ClickInterface{
                        override fun setOnClick(pos: Int) {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("id", listProduct[pos].id)
                            startActivity(intent)
                        }
                    })
                }
            }
        return view
    }
}