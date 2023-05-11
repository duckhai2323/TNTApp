package com.example.myapp1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.username
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShipedFragment1 : Fragment() {
    val db = Firebase.firestore
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_viewpageproduct, container, false)
        var rvShipped: RecyclerView = view.findViewById(R.id.rvProductOrder)
        var listOrder:MutableList<ItemBill1> = mutableListOf()
        db.collection("Shipped").whereEqualTo("receiver", username)
            .addSnapshotListener{result,e->
                listOrder.clear()
                for(document in result!!){
                    val id = document.data?.get("id").toString()
                    db.collection("products").document(id)
                        .get()
                        .addOnSuccessListener {it1->
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
                                listOrder.add(ItemBill1(idProduct,client,imageUrl[0],title,price,city,"Giao dịch thành công"))
                            }
                            rvShipped.adapter = ViewItemBill1Adapter(listOrder,R.drawable.background_button3,object:ClickInterface{
                                override fun setOnClick(pos: Int) {
                                }
                            })
                            rvShipped .layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                        }
                }

            }
        return view
    }
}