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
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.HomeActivity
import com.example.myapp1.home.ItemImageText
import com.example.myapp1.home.ItemProduct
import com.example.myapp1.home.adapter.ViewItemAdapter0
import com.example.myapp1.home.adapter.ViewProductAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import me.relex.circleindicator.CircleIndicator3

class DetailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
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
        listProduct1.add((ItemProduct(R.drawable.product2,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product3,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product4,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product5,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product3,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))
        listProduct1.add((ItemProduct(R.drawable.product2,"[Mã ELAP500K giảm 8% đơn 500K] Apple AirPods ...","đ28.000.000","Đã bán 10.6k")))

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
        val circle1 = findViewById<CircleIndicator3>(R.id.circle1)
        var list:MutableList<Int> = mutableListOf()
        list.add(R.drawable.detail1)
        list.add(R.drawable.detail2)
        list.add(R.drawable.detail3)
        list.add(R.drawable.detail4)
        list.add(R.drawable.detail5)
        list.add(R.drawable.detail3)
        vpDetail.adapter = ViewPageDetailAdapter(list)
        circle1.setViewPager(vpDetail)

        val rvDetail = findViewById<RecyclerView>(R.id.rvDetail)
        var listDetail:MutableList<ItemImageText> = mutableListOf()
        listDetail.add(ItemImageText(R.drawable.detail1,""))
        listDetail.add(ItemImageText(R.drawable.detail2,""))
        listDetail.add(ItemImageText(R.drawable.detail3,""))
        listDetail.add(ItemImageText(R.drawable.detail4,""))
        listDetail.add(ItemImageText(R.drawable.detail5,""))
        listDetail.add(ItemImageText(R.drawable.detail2,""))
        rvDetail.adapter = ViewItemAdapter0(listDetail)
        rvDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        var txtPrice0 = findViewById<TextView>(R.id.txtPrice0)
        val price:String = "đ259.000"
        val spannableString1 = SpannableString(price)
        spannableString1.setSpan(StrikethroughSpan(),0,price.length,0)
        txtPrice0.text = spannableString1
    }
}