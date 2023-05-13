package com.example.myapp1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClientActivity : AppCompatActivity() {
    private lateinit var clientName:String
    private val db = Firebase.firestore
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        val i = intent
        clientName = i.getStringExtra("clientProfile").toString()
        var txtAccount = findViewById<TextView>(R.id.txtAccount)
        txtAccount.text = clientName
        addEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent() {
        DisplayClientInfor()
        DisplayBangTin()
        DisplayComplete()
        DisplayTelephoneNumber()
        Back()
    }

    private fun Back() {
        var back = findViewById<ImageView>(R.id.backFromClient)
        back.setOnClickListener{
            onBackPressed()
        }
    }

    private fun DisplayTelephoneNumber() {
        var txtNumberPhone = findViewById<TextView>(R.id.txtTelephone)
        var txtDisplayNumber = findViewById<TextView>(R.id.txtDisplayNumber)
        txtDisplayNumber.setOnClickListener{
            db.collection("users").document(clientName)
                .get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        txtNumberPhone.text = it.data?.get("numberPhone").toString()
                        txtDisplayNumber.visibility = View.GONE
                    }
                }
        }
    }

    private fun DisplayClientInfor() {
        var txtName1 = findViewById<TextView>(R.id.txtName1)
        var txtDangBan1 = findViewById<TextView>(R.id.txtDangBan1)
        var txtDaBan1 = findViewById<TextView>(R.id.txtDaBan1)
        var clientFullName = findViewById<TextView>(R.id.clientName)
        var clientImage = findViewById<ImageView>(R.id.imageClient)
        var dateCreate = findViewById<TextView>(R.id.txtBirth)
        var txtNumberPhone = findViewById<TextView>(R.id.txtTelephone)
        var txtEmail = findViewById<TextView>(R.id.clientEmail)
        var txtTin1 = findViewById<TextView>(R.id.txtTin1)
        var txtTin2 = findViewById<TextView>(R.id.txtTin2)
        db.collection("users").document(clientName)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    txtName1.text = it.data?.get("fullName").toString()
                    Picasso.get().load(it.data?.get("imageProfile").toString()).into(clientImage)
                    val dangBanList =  it.data?.get("dangban") as MutableList<String>
                    txtDangBan1.text = "Đang bán: "+dangBanList.size.toString()
                    txtTin1.text = "- ${dangBanList.size} Tin"
                    val daBanList = it.data?.get("daban") as MutableList<String>
                    txtDaBan1.text = "Đã bán: "+daBanList.size.toString()
                    txtTin2.text = "- ${daBanList.size} Tin"
                    clientFullName.text = txtName1.text

                    val timestamp = it.getTimestamp("timestamp")
                    val date = timestamp?.toDate()
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    dateCreate.text = dateFormat.format(date)

                    val mNumberPhone = it.data?.get("numberPhone").toString()
                    txtNumberPhone.text = mNumberPhone.substring(0,6)+"***"
                    txtEmail.text = it.data?.get("email").toString()
                }
            }
    }

    private fun DisplayComplete() {
        var listTinCompelete:MutableList<String> = mutableListOf()
        var rvBanthanhcong = findViewById<RecyclerView>(R.id.rvBanthanhcong)
        if(listTinCompelete.size > 3) {
            var listTinComplete1:MutableList<String> = mutableListOf()
            val xemtatca = findViewById<LinearLayout>(R.id.xemtatca2)
            xemtatca.visibility = View.VISIBLE
            listTinComplete1.add(listTinCompelete[0])
            listTinComplete1.add(listTinCompelete[1])
            listTinComplete1.add(listTinCompelete[2])
            rvBanthanhcong.adapter = ViewItemAdapterTin1(listTinComplete1)
        } else {
            val xemtatca = findViewById<LinearLayout>(R.id.xemtatca2)
            xemtatca.visibility = View.GONE
            rvBanthanhcong.adapter = ViewItemAdapterTin1(listTinCompelete)
        }
        rvBanthanhcong.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun DisplayBangTin() {
        var txtAddress = findViewById<TextView>(R.id.txtClientAddress)
        var listTin:MutableList<ItemProduct> = mutableListOf()
        var rvDangsp = findViewById<RecyclerView>(R.id.rvDangsp)
        db.collection("products").whereEqualTo("username",clientName).whereEqualTo("display","true")
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(document in it.documents) {
                        val imageUrl = document.data?.get("picture") as MutableList<String>
                        var title = document.data?.get("title").toString()
                        var price = document.data?.get("price").toString()
                        var city  = document.data?.get("city").toString()
                        var  idProduct = document.data?.get("id").toString()
                        txtAddress.text = document.data?.get("address").toString()
                        val timestamp = document.getTimestamp("timestamp")
                        var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                        val txtTimeCount = mTimeCount?.timeCount()
                        city = "$city . $txtTimeCount"
                        listTin.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                    }

                    if(listTin.size > 3) {
                        var listTin1:MutableList<ItemProduct> = mutableListOf()
                        val xemtatca = findViewById<LinearLayout>(R.id.xemtatca1)
                        xemtatca.visibility = View.VISIBLE
                        listTin1.add(listTin[0])
                        listTin1.add(listTin[1])
                        listTin1.add(listTin[2])
                        rvDangsp.adapter = ViewItemAdapterTin(listTin1,object:ClickInterface{
                            override fun setOnClick(pos: Int) {
                                val i1 = Intent(this@ClientActivity, DetailActivity::class.java)
                                i1.putExtra("id",listTin1[pos].id)
                                startActivity(i1)
                            }
                        })
                    } else {
                        val xemtatca = findViewById<LinearLayout>(R.id.xemtatca1)
                        xemtatca.visibility = View.GONE
                        rvDangsp.adapter = ViewItemAdapterTin(listTin,object:ClickInterface{
                            override fun setOnClick(pos: Int) {
                                val i1 = Intent(this@ClientActivity, DetailActivity::class.java)
                                i1.putExtra("id",listTin[pos].id)
                                startActivity(i1)
                            }
                        })
                    }
                    rvDangsp.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                }
            }

    }
}