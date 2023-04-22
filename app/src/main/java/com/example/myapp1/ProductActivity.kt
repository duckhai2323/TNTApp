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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var brand_:String?=null
lateinit var category:String
lateinit var product:String
class ProductActivity : AppCompatActivity(), OnInputData1 {
    lateinit var addressStr:String
    lateinit var address:TextView
    lateinit var viewAdapter:ViewItemFilteAdapter
    lateinit var rvProductFilter:RecyclerView
    var listFilte:MutableList<FilteItem> = mutableListOf()
    lateinit var dialogFilte:DialogFilter
    var listProductFilter = mutableListOf<ItemProduct>()
    var mapFilter = mutableMapOf<String,String>()
    var position:Int = 0
    private val db = Firebase.firestore
    var check:Boolean = true
    override fun sendData(str: String, key1: String,key2:String) {
        mapFilter[key1] = str
        listProductFilter.clear()
        if(key1 == "address") {
            address.text = str
            addressStr = str
        } else {
            var item:FilteItem
            if(key2 == "accept") {
                item = FilteItem(str, R.drawable.outline_keyboard_arrow_down_24, R.drawable.background_button2_1, key1)
            } else {
                item = FilteItem(str, R.drawable.baseline_add_24_1, R.drawable.background_filter, key1)
                mapFilter.remove(key1)
            }
            if(key1 == "brand"){
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
            } else if(key1 == category){
                if(str != product) {
                    product = str
                    mapFilter.clear()
                    mapFilter["address"] = addressStr
                    listFilte.clear()
                    createListFilter()
                    displayListFilter()
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
                dialogFilte = DialogFilter("address")
                dialogFilte.show(supportFragmentManager,"aaa")
            }
            category = bundle.getString("category").toString()
            product = bundle.getString("product").toString()
            createListFilter()
        }
        displayListFilter()
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

    private fun displayListFilter() {
        var rvList = findViewById<RecyclerView>(R.id.rvListFilte)
        viewAdapter = ViewItemFilteAdapter(listFilte,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                position = pos
                dialogFilte = DialogFilter(listFilte[pos].key)
                dialogFilte.show(supportFragmentManager,"aaa")
            }
        })
        rvList.adapter = viewAdapter
        rvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
    }

    private fun createListFilter() {
        when(category){
            "electron" -> {
                when(product){
                    "Điện thoại" -> {
                        product = "telephone"
                        listFilte.add(FilteItem("Điện thoại",R.drawable.outline_keyboard_arrow_down_24,R.drawable.background_button2_1,"electron"))
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
                        product="laptop"
                        listFilte.add(FilteItem("Laptop",R.drawable.outline_keyboard_arrow_down_24,R.drawable.background_button2_1,"electron"))
                        listFilte.add(FilteItem("Hãng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"brand"))
                        listFilte.add(FilteItem("Dòng máy",R.drawable.baseline_add_24_1,R.drawable.background_filter,"series"))
                        listFilte.add(FilteItem("Giá",R.drawable.baseline_add_24_1,R.drawable.background_filter,"price"))
                        listFilte.add(FilteItem("Tình trạng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"status"))
                        listFilte.add(FilteItem("Bảo hành",R.drawable.baseline_add_24_1,R.drawable.background_filter,"warranty"))
                        listFilte.add(FilteItem("Kích cỡ màn hình",R.drawable.baseline_add_24_1,R.drawable.background_filter,"size"))
                        listFilte.add(FilteItem("Bộ vi xử lí",R.drawable.baseline_add_24_1,R.drawable.background_filter,"handle"))
                        listFilte.add(FilteItem("RAM",R.drawable.baseline_add_24_1,R.drawable.background_filter,"ram"))
                        listFilte.add(FilteItem("Card màn hình",R.drawable.baseline_add_24_1,R.drawable.background_filter,"card"))
                        listFilte.add(FilteItem("Ổ cứng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"ssd"))
                        listFilte.add(FilteItem("Màu sắc",R.drawable.baseline_add_24_1,R.drawable.background_filter,"color"))
                        listFilte.add(FilteItem("Thời gian sử dụng",R.drawable.baseline_add_24_1,R.drawable.background_filter,"time"))
                        listFilte.add(FilteItem("Vận chuyển giao nhận",R.drawable.baseline_add_24_1,R.drawable.background_filter,"ship"))
                    }
                }
            }
        }
    }

    private fun FilterProduct() {
        listProductFilter.clear()
        val dbRef = db.collection("products").document(category).collection(product)
        var query: Query = dbRef
        for((field,value) in mapFilter){
            if(field == "price"){
                continue
            }
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
                        var city  = document.data?.get("address").toString()

                        if(mapFilter["price"]!=null) {
                            val priceStr = mapFilter["price"].toString()
                            val index:Int = priceStr.lastIndexOf("-")
                            var fromPrice:Float
                            if(priceStr[0] == '0'){
                                fromPrice = 0F
                            } else if(priceStr[3] == '.') {
                                fromPrice = priceStr.substring(startIndex = 0, endIndex = 2).toFloat()/1000
                            }
                            else {
                                 fromPrice = priceStr.substring(startIndex = 0, endIndex = 3).toFloat()
                            }
                            val toPrice = priceStr.substring(startIndex = (index+2), endIndex = (index+5)).toFloat()
                            val priceProduct = price.substring(startIndex = 0, endIndex = 3).toFloat()
                            if(priceProduct in fromPrice..toPrice) {
                                listProductFilter.add(ItemProduct(imageUrl[0],title,price,city))
                            }
                        } else {
                            listProductFilter.add(ItemProduct(imageUrl[0],title,price,city))
                        }
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