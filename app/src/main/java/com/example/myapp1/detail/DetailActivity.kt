package com.example.myapp1.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import at.blogc.android.views.ExpandableTextView
import com.example.myapp1.CartActivity
import com.example.myapp1.ChatActivity
import com.example.myapp1.ClientActivity
import com.example.myapp1.PayActivity
import com.example.myapp1.R
import com.example.myapp1.TimeCount
import com.example.myapp1.ViewItemProduct1Adapter
import com.example.myapp1.ViewItemProduct2Adapter
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
import com.example.myapp1.home.adapter.ViewItemAdapter1
import com.example.myapp1.home.username
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator3
import java.util.MissingFormatArgumentException

class DetailActivity : AppCompatActivity() {
    lateinit var id:String
    private val db = Firebase.firestore
    lateinit var txtPrice:TextView
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val i = intent
        id = i.getStringExtra("id").toString()
        addEvent()
        txtPrice = findViewById<TextView>(R.id.txtPrice1)

        val btnReturn = findViewById<ImageView>(R.id.btnReturn)
        btnReturn.setOnClickListener{
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent() {
        DisplayImg()
        DisplayClientProduct()
        DisplayMota()
        DisplayClient()
        DisplayProduct()
        DisplayDialog()
        Chat()
        AddToCart()
        DisplayCart()
        StartPay()
    }

    private fun StartPay() {
        var txtShopping = findViewById<TextView>(R.id.txtShopping)
        var listIsChecked:MutableList<String> = mutableListOf()
        listIsChecked.add(id)
        txtShopping.setOnClickListener{
            val i = Intent(this@DetailActivity, PayActivity::class.java)
            i.putExtra("listIsChecked",listIsChecked.toTypedArray())
            i.putExtra("quantity",1)
            val txtPrice1 = txtPrice.text.toString()
            val priceStr = txtPrice1.substring(0,txtPrice1.length-1)
            val priceLong = priceStr.replace(".","").toLong()
            i.putExtra("price",priceLong)
            startActivity(i)
        }
    }

    private fun DisplayCart() {
        var imgCart = findViewById<ImageView>(R.id.imgCartDetail)
        imgCart.setOnClickListener{
            val i = Intent(this@DetailActivity, CartActivity::class.java)
            startActivity(i)
        }
    }

    private fun AddToCart() {
        var imgAddToCart = findViewById<ImageView>(R.id.imgAddToCart)
        var listID:MutableList<String> = mutableListOf()
        var updates:Map<String,Any>
        var client:String
        imgAddToCart.setOnClickListener{
            db.collection("users").document(username)
                .get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        client = it.data?.get("username").toString()
                        listID = it.data?.get("cart") as MutableList<String>
                        if(checkAlikeList(listID)) {
                            Toast.makeText(this,"Sản phẩm đã có trong giỏ hàng",Toast.LENGTH_SHORT).show()
                        } else {
                            listID.add(id)
                            updates = hashMapOf("cart" to listID)
                            db.collection("users").document(client).update(updates)
                            Toast.makeText(this,"Sản phẩm đã được thêm vào giỏ hàng",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    private fun checkAlikeList(listID: MutableList<String>): Boolean {
        for(i in listID) {
            if(i == id) {
                return true
            }
        }
        return false
    }


    private fun Chat() {
        var mImageProfile:String
        var chatButton = findViewById<ImageView>(R.id.imgChat)
        chatButton.setOnClickListener{
            db.collection("products").document(id)
                .get().addOnSuccessListener { it ->
                    if (it.exists()) {
                        val name = it.data?.get("username").toString()
                        db.collection("users").document(name)
                            .get()
                            .addOnSuccessListener {it1->
                                if(it1.exists()) {
                                    mImageProfile = it1.data?.get("imageProfile").toString()
                                    val intent = Intent(this, ChatActivity::class.java)
                                    intent.putExtra("name", name)
                                    intent.putExtra("imageProfile",mImageProfile)
                                    startActivity(intent)
                                }
                        }
                    }
                }
        }
    }

    private fun StartClient(clientName:String) {
        val lotCilent = findViewById<ConstraintLayout>(R.id.txtXemTrangCN)
        lotCilent.setOnClickListener {
            val i = Intent(this, ClientActivity::class.java)
            i.putExtra("clientProfile",clientName)
            startActivity(i)
        }
    }

    private fun DisplayDialog() {
        val txtdialog = findViewById<TextView>(R.id.txtdialog)
        txtdialog.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val view: View = layoutInflater.inflate(R.layout.layout_dialog1,null)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun DisplayProduct() {
        val rvProduct = findViewById<RecyclerView>(R.id.rvProductDetail)
        val listProduct1:MutableList<ItemProduct> = mutableListOf()
        db.collection("products").document(id)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    val strCategory = it.data?.get("category").toString()
                    db.collection("products").whereEqualTo("category",strCategory).whereEqualTo("display","true")
                        .addSnapshotListener{ result,e->
                            if(result != null){
                                listProduct1.clear()
                                for(document in result!!){
                                    val imageUrl = document.data?.get("picture") as MutableList<String>
                                    var title = document.data?.get("title").toString()
                                    var price = document.data?.get("price").toString()
                                    var city  = document.data?.get("city").toString()
                                    var  idProduct = document.data?.get("id").toString()
                                    val timestamp = document.getTimestamp("timestamp")
                                    var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                                    val txtTimeCount = mTimeCount?.timeCount()
                                    city = "$city . $txtTimeCount"
                                    if(idProduct!=id && document.data?.get("username").toString() != username) {
                                        listProduct1.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                                    }
                                }
                                rvProduct.adapter = ViewItemProduct2Adapter(listProduct1,object : ClickInterface {
                                    override fun setOnClick(pos: Int) {
                                        val i1 = Intent(this@DetailActivity, DetailActivity::class.java)
                                        i1.putExtra("id",listProduct1[pos].id)
                                        startActivity(i1)
                                    }
                                })
                                rvProduct.layoutManager = GridLayoutManager(
                                    this,2
                                )
                            }
                        }
                }
            }

    }

    private fun DisplayClient() {
        var imgProfile = findViewById<ImageView>(R.id.imgProfile)
        var txtProfile = findViewById<TextView>(R.id.txtProfile)
        var txtDangBan = findViewById<TextView>(R.id.txtDangBan)
        var txtDaBan = findViewById<TextView>(R.id.txtBanXong)
        var txtClientProduct = findViewById<TextView>(R.id.txtClientProduct)
        db.collection("products")
            .document(id)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    val client = it.data?.get("username").toString()
                    db.collection("users").document(client)
                        .get()
                        .addOnSuccessListener {
                            if(it.exists()) {
                                val ImageUrl = it.data?.get("imageProfile").toString()
                                Picasso.get().load(ImageUrl).into(imgProfile)
                                txtProfile.text = it.data?.get("fullName").toString()
                                txtClientProduct.text = "Các sản phẩm khác của " + it.data?.get("fullName").toString()
                                val DangBanList = it.data?.get("dangban") as MutableList<String>
                                txtDangBan.text = "Đang bán: "+ DangBanList.size.toString() + " sản phẩm"
                                val DaBanList = it.data?.get("daban") as MutableList<String>
                                txtDaBan.text = "Đã bán: " + DaBanList.size.toString() + " sản phẩm"
                            }
                        }
                    StartClient(client)
                }
            }
    }

    private fun DisplayMota() {
        val expandableTextView = findViewById<ExpandableTextView>(R.id.txtDescription)
        val txtRead = findViewById<TextView>(R.id.txtRead)
        db.collection("products")
            .document(id)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    val description = it.data?.get("description").toString()
                    val fomartString = description?.replace("\\n","\n")
                    expandableTextView.text = fomartString
                    expandableTextView.setInterpolator(OvershootInterpolator())
                }
            }
        txtRead.setOnClickListener{
            if(expandableTextView.isExpanded) {
                expandableTextView.collapse()
                txtRead.setText("Xem thêm")
            } else{
                expandableTextView.expand()
                txtRead.setText("Thu gọn")
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun DisplayClientProduct() {
        var rvTuongtu = findViewById<RecyclerView>(R.id.tuongtu)
        var listTuongTu:MutableList<ItemProduct> = mutableListOf()
        var txtMore = findViewById<TextView>(R.id.txtMoreProduct)
        db.collection("products")
            .document(id)
            .get().addOnSuccessListener {
                if(it.exists()) {
                    val client = it.data?.get("username").toString()
                    db.collection("products").whereEqualTo("username", client).whereEqualTo("display","true")
                        .addSnapshotListener{ result,e->
                            if(result != null) {
                                listTuongTu.clear()
                                for(document in result!!) {
                                    val imageUrl = document.data?.get("picture") as MutableList<String>
                                    var title = document.data?.get("title").toString()
                                    var price = document.data?.get("price").toString()
                                    var city  = document.data?.get("city").toString()
                                    var  idProduct = document.data?.get("id").toString()
                                    val timestamp = document.getTimestamp("timestamp")
                                    var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                                    val txtTimeCount = mTimeCount?.timeCount()
                                    city = "$city . $txtTimeCount"
                                    if(idProduct!=id) {
                                        listTuongTu.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                                    }
                                }
                                rvTuongtu.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

                                if(listTuongTu.size > 3) {
                                    var listTin1:MutableList<ItemProduct> = mutableListOf()
                                    txtMore.visibility = View.VISIBLE
                                    listTin1.add(listTuongTu[0])
                                    listTin1.add(listTuongTu[1])
                                    listTin1.add(listTuongTu[2])
                                    rvTuongtu.adapter = ViewItemProduct1Adapter(listTin1,object:ClickInterface{
                                        override fun setOnClick(pos: Int) {
                                            val i1 = Intent(this@DetailActivity, DetailActivity::class.java)
                                            i1.putExtra("id",listTuongTu[pos].id)
                                            startActivity(i1)
                                        }
                                    })
                                } else {
                                    txtMore.visibility = View.GONE
                                    rvTuongtu.adapter = ViewItemProduct1Adapter(listTuongTu,object:ClickInterface{
                                        override fun setOnClick(pos: Int) {
                                            val i1 = Intent(this@DetailActivity, DetailActivity::class.java)
                                            i1.putExtra("id",listTuongTu[pos].id)
                                            startActivity(i1)
                                        }
                                    })
                                }
                            }
                        }
                    }
               }
           }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun DisplayImg() {
        val vpDetail = findViewById<ViewPager2>(R.id.vpDetail)
        val rvDetail = findViewById<RecyclerView>(R.id.rvDetail)
        val circle1 = findViewById<CircleIndicator3>(R.id.circle1)
        var txtTitle = findViewById<TextView>(R.id.txtTitle)
        var txtTime = findViewById<TextView>(R.id.txtTime)
        var txtAddress = findViewById<TextView>(R.id.txtClientAddress)
        db.collection("products")
            .document(id)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    val listImageProduct = it.data?.get("picture") as MutableList<String>
                    vpDetail.adapter = ViewPageDetailAdapter(listImageProduct)
                    circle1.setViewPager(vpDetail)
                    rvDetail.adapter = ViewItemAdapter1(listImageProduct,object:ClickInterface{
                        override fun setOnClick(pos: Int) {
                        }
                    })
                    rvDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    txtTitle.text = it.data?.get("title").toString()
                    txtPrice.text = it.data?.get("price").toString()
                    txtAddress.text = it.data?.get("address").toString()
                    val timestamp:Timestamp? = it.getTimestamp("timestamp")
                    val mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                    txtTime.text = mTimeCount?.timeCount()
                }
            }
    }
}