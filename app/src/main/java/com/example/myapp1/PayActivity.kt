package com.example.myapp1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.email
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.util.Locale

class PayActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    lateinit var listId:Array<String>
    var quantity:Int = 0
    var price:Long = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        val i = intent
        listId = i.getStringArrayExtra("listIsChecked") as Array<String>
        quantity = i.getIntExtra("quantity",0)
        price = i.getLongExtra("price",0)
        addEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent() {
        DisplayInfor()
        DisplayProduct()
        Order()
    }

    private fun DisplayInfor() {
        var username:String
        var txtName = findViewById<TextView>(R.id.txtName)
        var txtNumberPhone = findViewById<TextView>(R.id.txtNumberPhone)
        var txtInfor = findViewById<TextView>(R.id.txtInfor)
        db.collection("users").whereEqualTo("email", email)
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(document in it.documents) {
                        val addressMap:Map<String,String> = document.data?.get("address") as Map<String, String>
                        txtName.text = addressMap["name"].toString()
                        txtNumberPhone.text = addressMap["numberPhoneX"].toString()
                        txtInfor.text = addressMap["addressInfor"].toString()
                    }
                }
            }
    }

    private fun Order() {
        var txtPrice = findViewById<TextView>(R.id.price)
        var txtOrder = findViewById<TextView>(R.id.txtOrder)
        val priceStr  = NumberFormat.getNumberInstance(Locale("vi", "VN"))
            .format(price)
            .replace(",", ".")
        txtPrice.text = "$priceStr đ"
        txtOrder.text = "Đặt hàng ($quantity)"
        txtOrder.setOnClickListener{

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun DisplayProduct() {
        var rvListPay = findViewById<RecyclerView>(R.id.rvListPay)
        var listProduct:MutableList<ItemViewPay> = mutableListOf()
        for(i in listId){
            db.collection("products").document(i)
                .get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        val client = it.data?.get("username").toString()
                        val imageUrl = it.data?.get("picture") as MutableList<String>
                        var title = it.data?.get("title").toString()
                        var price = it.data?.get("price").toString()
                        var city  = it.data?.get("city").toString()
                        var  idProduct =it.data?.get("id").toString()
                        val timestamp = it.getTimestamp("timestamp")
                        var mTimeCount = timestamp?.let { it -> TimeCount(it) }
                        val txtTimeCount = mTimeCount?.timeCount()
                        city = "$city . $txtTimeCount"
                        listProduct.add(ItemViewPay(idProduct,client,imageUrl[0],title,price,city,""))
                    }
                    rvListPay.adapter = ViewItemPayAdapter(this,listProduct)
                    rvListPay.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                }
        }
    }
}