package com.example.myapp1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.username
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
        UpdateAdress()
        DisplayProduct()
        Order()
        StartBill()
        Back()
    }

    private fun Back() {
        var back = findViewById<ImageView>(R.id.backFromCart)
        back.setOnClickListener{
            onBackPressed()
        }
    }

    private fun UpdateAdress() {
        var addressConstrant = findViewById<ConstraintLayout>(R.id.addressConstrant)
        addressConstrant.setOnClickListener{
            val i = Intent(this@PayActivity, UpdateaddressActivity::class.java)
            startActivity(i)
        }
    }

    private fun StartBill() {
        var txtOrder = findViewById<TextView>(R.id.txtOrder)
        txtOrder.setOnClickListener{
           for(id in listId) {
               db.collection("products").document(id).update("display","false")
               db.collection("products").document(id)
                   .get()
                   .addOnSuccessListener {
                       if(it.exists()) {
                           val seller = it.data?.get("username").toString()
                           val waitConfirm:Map<String,String> = mapOf(
                               "seller" to seller,
                               "receiver" to username,
                               "id" to id
                           )
                           val ref = db.collection("WaitConfirm")
                           ref.document(ref.document().id).set(waitConfirm)

                           /*db.collection("users").document(seller)
                               .get()
                               .addOnSuccessListener {it1->
                                   if(it1.exists()) {
                                       var listDaban = it1.data?.get("daban") as MutableList<String>
                                       listDaban.add(id)
                                       db.collection("users").document(seller).update("daban",listDaban)

                                       var listDangban = it1.data?.get("dangban") as MutableList<String>
                                       listDangban.remove(id)
                                       db.collection("users").document(seller).update("dangban",listDangban)
                                   }
                               }*/
                       }

                   }
           }
            db.collection("users").document(username)
                .get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        val listCart = it.data?.get("cart")  as MutableList<String>
                        for(id in listId) {
                            listCart.remove(id)
                        }
                        db.collection("users").document(username).update("cart",listCart)
                    }
                }
            val i = Intent(this@PayActivity,BillActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun DisplayInfor() {
        var username:String
        var txtName = findViewById<TextView>(R.id.txtName)
        var txtNumberPhone = findViewById<TextView>(R.id.txtNumberPhone)
        var txtInfor = findViewById<TextView>(R.id.txtInfor)
        db.collection("users").document(com.example.myapp1.home.username)
            .addSnapshotListener {result,e->
                if(result != null) {
                    val it = result
                    val addressMap:Map<String,String> = it.data?.get("address") as Map<String, String>
                    txtName.text = addressMap["name"].toString()
                    txtNumberPhone.text = addressMap["numberPhoneX"].toString()
                    txtInfor.text = addressMap["addressInfor"].toString()
                    if(txtName.text.toString() == "" && txtNumberPhone.text.toString() == "" && txtInfor.text.toString() == "") {
                        var inforUser = findViewById<LinearLayout>(R.id.NameNumberPhone)
                        inforUser.visibility = View.GONE
                        txtInfor.visibility = View.GONE
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