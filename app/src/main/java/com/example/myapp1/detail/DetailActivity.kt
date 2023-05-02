package com.example.myapp1.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import at.blogc.android.views.ExpandableTextView
import com.example.myapp1.ClientActivity
import com.example.myapp1.R
import com.example.myapp1.category
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.HomeActivity
import com.example.myapp1.home.ItemProduct
import com.example.myapp1.home.adapter.ViewItemAdapter1
import com.example.myapp1.home.adapter.ViewProductAdapter
import com.example.myapp1.product
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.relex.circleindicator.CircleIndicator3

class DetailActivity : AppCompatActivity() {
    lateinit var id:String
    private val db = Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val i = intent
        id = i.getStringExtra("id").toString()
        print(id)
        addEvent()

        val btnReturn = findViewById<ImageView>(R.id.btnReturn)
        btnReturn.setOnClickListener{
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)
        }
    }

    private fun addEvent() {
        DisplayImg()
        DisplayMota()
        DisplayProduct()
        DisplayDialog()
        StartClient()
    }

    private fun StartClient() {
        val lotCilent = findViewById<ConstraintLayout>(R.id.txtXemTrangCN)
        lotCilent.setOnClickListener {
            val i = Intent(this, ClientActivity::class.java)
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

    private fun DisplayProduct() {
        val rvProduct = findViewById<RecyclerView>(R.id.rvProductDetail)
        val listProduct1:MutableList<ItemProduct> = mutableListOf()
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct("",resources.getString(R.string.linkImage),"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))

        rvProduct.adapter = ViewProductAdapter(listProduct1,object : ClickInterface {
            override fun setOnClick(pos: Int) {

            }
        })
        rvProduct.layoutManager = GridLayoutManager(
            this,2
        )
    }

    private fun DisplayMota() {
        val expandableTextView = findViewById<ExpandableTextView>(R.id.expContent_View)
        val txtRead = findViewById<TextView>(R.id.txtRead)
        expandableTextView.setInterpolator(OvershootInterpolator())
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

    private fun DisplayImg() {
        val vpDetail = findViewById<ViewPager2>(R.id.vpDetail)
        val rvDetail = findViewById<RecyclerView>(R.id.rvDetail)
        val circle1 = findViewById<CircleIndicator3>(R.id.circle1)
        var txtTitle = findViewById<TextView>(R.id.txtTitle)
        var txtTime = findViewById<TextView>(R.id.txtTime)
        var txtPrice = findViewById<TextView>(R.id.txtPrice1)
        var txtAddress = findViewById<TextView>(R.id.txtAddress)
        db.collection("products").document(category)
            .collection(product)
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
                }
            }
    }
}