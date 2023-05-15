package com.example.myapp1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.HomeActivity
import com.example.myapp1.home.ItemProduct
import com.example.myapp1.home.username
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var brand_:String = ""
lateinit var category:String
lateinit var product:String
class ProductActivity : AppCompatActivity(), OnInputData1 {
    lateinit var cityStr:String
    lateinit var city:TextView
    lateinit var viewAdapter:ViewItemFilteAdapter1
    lateinit var rvProductFilter:RecyclerView
    var listFilte:MutableList<FilteItem> = mutableListOf()
    var listFilteProduct:MutableList<ItemFiltte> = mutableListOf()
    lateinit var dialogFilte:DialogFilter
    lateinit var dialogFilteProduct:DialogFilterProduct
    var listProductFilter = mutableListOf<ItemProduct>()
    var mapFilter = mutableMapOf<String,String>()
    var position:Int = 0
    private val db = Firebase.firestore
    private lateinit var id:String
    var check:Boolean = true
    @RequiresApi(Build.VERSION_CODES.O)
    override fun sendData(str: String, key1: String, key2:String) {
        var index = position
        mapFilter[key1] = str
        listProductFilter.clear()
        if(key1 == "city") {
            city.text = str
            cityStr = str
        } else {
            var item:FilteItem
            if(key2 == "accept") {
                item = FilteItem(str, R.drawable.outline_keyboard_arrow_down_24, R.drawable.background_button2_1, key1)
            } else {
                item = FilteItem(str, R.drawable.baseline_add_24_1, R.drawable.background_filter, key1)
                mapFilter.remove(key1)
            }
            if(key1 == "brand" ){
                index = 1
                if(listFilte[index].TextItem != brand_) {
                    var item1 = FilteItem(
                        "Dòng máy",
                        R.drawable.baseline_add_24_1,
                        R.drawable.background_filter,
                        "series"
                    )
                    viewAdapter.updateData(item1, index + 1)
                    mapFilter.remove("series")
                }
            } else if(key1 == category){
                if(str != product) {
                    product = str
                    mapFilter.clear()
                    mapFilter["city"] = cityStr
                    listFilte.clear()
                    createListFilter()
                    displayListFilter()
                }
            }
            viewAdapter.updateData(item, index)
        }
        FilterProduct()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        brand_ = ""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        mapFilter["display"] ="true"
        city = findViewById<TextView>(R.id.cityText)
        val i = intent
        val bundle = i.extras

        if(bundle!=null) {
            city.text = bundle.getString("city")
            cityStr = city.text.toString()
            if(cityStr!="Toàn quốc") {
                mapFilter["city"] = cityStr
            }
            var cityLLO: LinearLayout = findViewById<LinearLayout>(R.id.cityLLO)
            cityLLO.setOnClickListener {
                dialogFilte = DialogFilter("city")
                dialogFilte.show(supportFragmentManager,"aaa")
            }
            category = bundle.getString("category").toString()
            product = bundle.getString("product").toString()
            createListFilter()
        }
        displayListFilter()
        var loc = findViewById<LinearLayout>(R.id.loc)
        loc.setOnClickListener{
            dialogFilteProduct = DialogFilterProduct(listFilteProduct)
            dialogFilteProduct.show(supportFragmentManager,"aaa")
        }
        rvProductFilter = findViewById<RecyclerView>(R.id.rvProductFilter)
        FilterProduct()
        var imgAdapter:ImageView = findViewById<ImageView>(R.id.adapter)
        imgAdapter.setOnClickListener{
            if(check) {
                rvProductFilter.adapter = ViewItemAdapterTin(listProductFilter,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        val i1 = Intent(this@ProductActivity, DetailActivity::class.java)
                        i1.putExtra("id",listProductFilter[pos].id)
                        startActivity(i1)
                    }
                })
                rvProductFilter.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                check = false
            } else {
                DisplayListProduct(listProductFilter)
                check = true
            }
        }

        var iconBack = findViewById<ImageView>(R.id.iconBack)
        iconBack.setOnClickListener{
            onBackPressed()
        }

        var imgCart = findViewById<ImageView>(R.id.imgCart)
        imgCart.setOnClickListener{
            val i = Intent(this@ProductActivity,CartActivity::class.java)
            startActivity(i)
        }

    }

    private fun displayListFilter() {
        var rvList = findViewById<RecyclerView>(R.id.rvListFilte)
        viewAdapter = ViewItemFilteAdapter1(listFilte,object:ClickInterface{
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
                    "telephone" -> {
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

                    "laptop" -> {
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
        for(i in listFilte){
            listFilteProduct.add(ItemFiltte(i.TextItem,"chưa lọc",
                ContextCompat.getColor(this,R.color.textFilter),i.key))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun FilterProduct() {
        listProductFilter.clear()
        val strCategory:String = "$category/$product"
        val dbRef = db.collection("products").whereEqualTo("category",strCategory)
        var query: Query = dbRef
        for((field,value) in mapFilter){
            if(field == "price"){
                continue
            }
            query = query.whereEqualTo(field,value)
        }
        query
            .addSnapshotListener{result,e->
                if(result !=null) {
                    listProductFilter.clear()
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

                        if (document.data?.get("username").toString() != username) {
                            if (mapFilter["price"] != null) {
                                val priceStr = mapFilter["price"].toString()
                                val index: Int = priceStr.lastIndexOf("-")
                                var fromPrice: Float
                                if (priceStr[0] == '0') {
                                    fromPrice = 0F
                                } else if (priceStr[3] == '.') {
                                    fromPrice = priceStr.substring(startIndex = 0, endIndex = 2)
                                        .toFloat() / 1000
                                } else {
                                    fromPrice =
                                        priceStr.substring(startIndex = 0, endIndex = 3).toFloat()
                                }
                                val toPrice = priceStr.substring(
                                    startIndex = (index + 2),
                                    endIndex = (index + 5)
                                ).toFloat()
                                val priceProduct =
                                    price.substring(startIndex = 0, endIndex = 3).toFloat()
                                if (priceProduct in fromPrice..toPrice) {
                                    listProductFilter.add(
                                        ItemProduct(
                                            idProduct,
                                            imageUrl[0],
                                            title,
                                            price,
                                            city
                                        )
                                    )
                                }
                            } else {
                                listProductFilter.add(
                                    ItemProduct(
                                        idProduct,
                                        imageUrl[0],
                                        title,
                                        price,
                                        city
                                    )
                                )
                            }
                        }
                    }
                    DisplayListProduct(listProductFilter)
                }
            }
    }

    private fun DisplayListProduct(listProductFilter: MutableList<ItemProduct>) {
        rvProductFilter.adapter = ViewItemProduct2Adapter(listProductFilter,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                val i1 = Intent(this@ProductActivity, DetailActivity::class.java)
                i1.putExtra("id",listProductFilter[pos].id)
                startActivity(i1)
            }
        })
        rvProductFilter.layoutManager = GridLayoutManager(
            this,2
        )
        check = true
    }
}