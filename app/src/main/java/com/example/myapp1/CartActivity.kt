package com.example.myapp1

import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.email
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        addEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent() {
        DisplayListCart()
        Back()
    }

    private fun Back() {
        var txtBack = findViewById<ImageView>(R.id.backFromCart)
        txtBack.setOnClickListener{
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun DisplayListCart() {
        var order = findViewById<LinearLayout>(R.id.order)
        var money = findViewById<TextView>(R.id.money)
        var quantity = findViewById<TextView>(R.id.quantity)
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
                                listProduct.add(ItemCart(idProduct,client,imageUrl[0],title,price,city,false))
                            }
                            val adapter_ = ViewItemCartAdapter(listProduct,this,object:ClickInterface{
                                override fun setOnClick(pos: Int) {
                                    var quantity_:Int = 0
                                    var money_:Long = 0
                                    for(i in listProduct){
                                        if(i.check) {
                                            val priceStr = i.txtPrice1.substring(0,i.txtPrice1.length-1)
                                            val priceLong = priceStr.replace(".","").toLong()
                                            money_ += priceLong
                                            quantity_++
                                        }
                                    }
                                    if(quantity_ == 0) {
                                        order.visibility = View.GONE
                                    } else order.visibility = View.VISIBLE
                                    quantity.text = "Mua hàng ($quantity_)"
                                    val formattedResult = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                                        .format(money_)
                                        .replace(",", ".")
                                    money.text = "$formattedResult đ"
                                }
                            })
                            rvListCart.adapter = adapter_
                            rvListCart.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                       }
                   }
                }
            }
    }
}