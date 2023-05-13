package com.example.myapp1

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.username
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShipedFragment2: Fragment() {
    val db = Firebase.firestore
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_viewpageproduct, container, false)
        var listProduct:MutableList<ItemBill2> = mutableListOf()
        var rvShippedManager: RecyclerView = view.findViewById(R.id.rvProductOrder)
        var listIdProduct:MutableList<Pair<String,String>> = mutableListOf()
        db.collection("Shipped").whereEqualTo("seller", username)
            .addSnapshotListener{result,e->
                val newListIdProduct:MutableList<Pair<String,String>> = mutableListOf()
                for(document in result!!.documents) {
                    var receiver = document.data?.get("receiver").toString()
                    var idProduct = document.data?.get("id").toString()
                    val pair:Pair<String,String> = Pair(receiver,idProduct)
                    newListIdProduct.add(pair)
                }
                listIdProduct.clear()
                listIdProduct.addAll(newListIdProduct)
                val newListProduct:MutableList<ItemBill2> = mutableListOf()
                for(pair in listIdProduct) {
                    var receiverMap:Map<String,String>
                    db.collection("products").document(pair.second)
                        .get()
                        .addOnSuccessListener { it1->
                            if(it1.exists()) {
                                val client = pair.first
                                db.collection("users").document(client)
                                    .get()
                                    .addOnSuccessListener {it2->
                                        if(it2.exists()) {
                                            receiverMap = it2.data?.get("address") as Map<String,String>
                                            val imageUrl = it1.data?.get("picture") as MutableList<String>
                                            var title = it1.data?.get("title").toString()
                                            var price = it1.data?.get("price").toString()
                                            var city  = it1.data?.get("city").toString()
                                            var  idProduct =it1.data?.get("id").toString()
                                            val timestamp = it1.getTimestamp("timestamp")
                                            var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                                            val txtTimeCount = mTimeCount?.timeCount()
                                            city = "$city . $txtTimeCount"
                                            newListProduct.add(ItemBill2(idProduct,imageUrl[0],title,price,city,"Giao dịch thành công",client,receiverMap["name"].toString(),receiverMap["numberPhoneX"].toString(),receiverMap["addressInfor"].toString()))
                                        }
                                        listProduct.clear()
                                        listProduct.addAll(newListProduct)
                                        rvShippedManager.adapter = ViewItemBill2Adapter(listProduct,R.drawable.background_button3,object:
                                            ClickInterface {
                                            override fun setOnClick(pos: Int) {
                                            }
                                        })
                                        rvShippedManager.layoutManager = LinearLayoutManager(context,
                                            LinearLayoutManager.VERTICAL,false)
                                    }
                            }
                        }
                }
            }
        return view
    }
}