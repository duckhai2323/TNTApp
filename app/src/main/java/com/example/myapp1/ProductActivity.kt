package com.example.myapp1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var brand_:String?=null
class ProductActivity : AppCompatActivity(), OnInputData {
    lateinit var addressStr:String
    lateinit var address:TextView
    lateinit var viewAdapter:ViewItemFilteAdapter
    var listFilte:MutableList<FilteItem> = mutableListOf()
    lateinit var dialogFilte:DialogFilter
    var listProductFilter = mutableListOf<ItemProduct>()
    lateinit var product_:String
    lateinit var rvProductFilter:RecyclerView
    var mapFilter = mutableMapOf<String,String>()
    var position:Int = 0
    private val db = Firebase.firestore
    var check:Boolean = true
    override fun sendData(str: String, key: String) {
        mapFilter[key] = str
        listProductFilter.clear()
        if(key == "address") {
            address.text = str
        } else {
            val item = FilteItem(
                str,
                R.drawable.outline_keyboard_arrow_down_24,
                R.drawable.background_button2_1,
                key
            )
            if(key == "brand"){
                brand_ = str
                if(mapFilter["series"]?.isEmpty() == false) {
                    var item1 = FilteItem(
                        "Dòng máy",
                        R.drawable.baseline_add_24_1,
                        R.drawable.background_filter,
                        "series"
                    )
                    viewAdapter.updateData(item1, position + 1)
                    mapFilter.remove("series")
                }
            }
            viewAdapter.updateData(item, position)
        }
        FilterProduct()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        address = findViewById<TextView>(R.id.addressText)
        val i = intent
        val bundle = i.extras

        if(bundle!=null) {
            address.text = bundle.getString("city")
            addressStr = address.text.toString()
            mapFilter["address"] = addressStr

            var addressLLO: LinearLayout = findViewById<LinearLayout>(R.id.addressLLO)
            addressLLO.setOnClickListener {
                dialogFilte = DialogFilter("address","aaa")
                dialogFilte.show(supportFragmentManager,"aaa")
            }
            product_ = bundle.getString("product").toString()
            when(product_){
                "Điện thoại" -> {
                    product_ = "telephone"
                    listFilte.add(FilteItem("Điện thoại",R.drawable.outline_keyboard_arrow_down_24,R.drawable.background_button2_1,"product"))
                    listFilte.add(FilteItem("Hãng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"brand"))
                    listFilte.add(FilteItem("Dòng máy",R.drawable.baseline_add_24_1,R.drawable.background_filter,"series"))
                    listFilte.add(FilteItem("Giá",R.drawable.baseline_add_24_1,R.drawable.background_filter,"price"))
                    listFilte.add(FilteItem("Tình trạng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"status"))
                    listFilte.add(FilteItem("Bảo hành",R.drawable.baseline_add_24_1,R.drawable.background_filter,"warranty"))
                    listFilte.add(FilteItem("Dung lượng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"capacity"))
                    listFilte.add(FilteItem("Màu sắc",R.drawable.baseline_add_24_1,R.drawable.background_filter,"color"))
                    listFilte.add(FilteItem("Thời gian sử dụng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"time"))
                }

                "Laptop" -> {
                    product_="laptop"
                    listFilte.add(FilteItem("Laptop",R.drawable.outline_keyboard_arrow_down_24,R.drawable.background_button2_1,"product"))
                    listFilte.add(FilteItem("Hãng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"brand"))
                    listFilte.add(FilteItem("Dòng máy",R.drawable.baseline_add_24_1,R.drawable.background_filter,"series"))
                    listFilte.add(FilteItem("Giá",R.drawable.baseline_add_24_1,R.drawable.background_filter,"price"))
                    listFilte.add(FilteItem("Tình trạng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"status"))
                    listFilte.add(FilteItem("Bảo hành",R.drawable.baseline_add_24_1,R.drawable.background_filter,"warranty"))
                    listFilte.add(FilteItem("RAM",R.drawable.baseline_add_24_1,R.drawable.background_filter,"ram"))
                    listFilte.add(FilteItem("Ổ cứng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"c"))
                    listFilte.add(FilteItem("Card màn hình",R.drawable.baseline_add_24_1,R.drawable.background_filter,"card"))
                    listFilte.add(FilteItem("Màu sắc",R.drawable.baseline_add_24_1,R.drawable.background_filter,"color"))
                    listFilte.add(FilteItem("Thời gian sử dụng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"time"))
                }
            }
        }
        var rvList = findViewById<RecyclerView>(R.id.rvListFilte)

        viewAdapter = ViewItemFilteAdapter(listFilte,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                position = pos
                dialogFilte = DialogFilter(listFilte[pos].key,product_)
                dialogFilte.show(supportFragmentManager,"aaa")
            }
        })
        rvList.adapter = viewAdapter
        rvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        rvProductFilter = findViewById<RecyclerView>(R.id.rvProductFilter)
        FilterProduct()
        var imgAdapter:ImageView = findViewById<ImageView>(R.id.adapter)
        imgAdapter.setOnClickListener{
            if(check) {
                rvProductFilter.adapter = ViewItemAdapterTin(listProductFilter)
                rvProductFilter.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                check = false
            } else {
                DisplayListProduct(listProductFilter)
                check = true
            }
        }
    }

    private fun FilterProduct() {
        listProductFilter.clear()
        val dbRef = db.collection("products").document("electron").collection(product_)
        var query: Query = dbRef
        for((field,value) in mapFilter){
            query = query.whereEqualTo(field,value)
        }
        query
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty) {
                    for(document in it.documents) {
                        val imageUrl = document.data?.get("picture") as MutableList<String>
                        var title = document.data?.get("title").toString()
                        var price = document.data?.get("price").toString()
                        listProductFilter.add(ItemProduct(imageUrl[0],title,price,addressStr))
                    }
                    DisplayListProduct(listProductFilter)
                }
            }
    }

    private fun DisplayListProduct(listProductFilter: MutableList<ItemProduct>) {
        rvProductFilter.adapter = ViewItemProduct2Adapter(listProductFilter,object:ClickInterface{
            override fun setOnClick(pos: Int) {

            }
        })
        rvProductFilter.layoutManager = GridLayoutManager(
            this,2
        )
        check = true
    }
}