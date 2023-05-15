package com.example.myapp1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.example.myapp1.home.ItemProduct
import com.example.myapp1.home.username
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import me.relex.circleindicator.CircleIndicator3

val TagElectron:String = ElectronicFragment::class.java.name
class ElectronicFragment : Fragment(){
    lateinit var txtCity:TextView
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View =  inflater.inflate(R.layout.fragment_electronic, container, false)
        var backFragmentHom:ImageView = view.findViewById(R.id.backFragmentHome)

        //selectCity
        var selectCity:LinearLayout = view.findViewById(R.id.selectCity)
        txtCity = view.findViewById(R.id.city)
        txtCity.text = "Hà Nội"
        selectCity.setOnClickListener {
            var dialogSelectCity:DialogSelectCity = DialogSelectCity()
            dialogSelectCity.show(requireFragmentManager(),"aaa")
        }


        //DisplaySlider()
        val viewPage2: ViewPager2 = view.findViewById(R.id.viewpage2)
        val circleindicator: CircleIndicator3 = view.findViewById(R.id.circledicator1)
        val listItem:MutableList<Int> = mutableListOf()
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        val adapter_ = ViewPageElecAdapter(listItem)
        viewPage2.adapter = adapter_
        circleindicator.setViewPager(viewPage2)

        //DisplaySuggest0
        var rvGoiYChuDe: RecyclerView = view.findViewById(R.id.rvGoiYElec)
        var listItemGoiY0:MutableList<ItemImageText> = mutableListOf()
        listItemGoiY0.add(ItemImageText(R.drawable.dienthoai,"Điện thoại","telephone"))
        listItemGoiY0.add(ItemImageText(R.drawable.maytinhbang,"Máy tính bảng","ipad"))
        listItemGoiY0.add(ItemImageText(R.drawable.laptop,"Laptop","laptop"))
        listItemGoiY0.add(ItemImageText(R.drawable.maytinh,"Máy tính để bàn","computer"))
        listItemGoiY0.add(ItemImageText(R.drawable.loa,"Loa","speaker"))
        listItemGoiY0.add(ItemImageText(R.drawable.tivi,"Tivi","tivi"))
        listItemGoiY0.add(ItemImageText(R.drawable.tainghe,"Tai nghe","earphone"))
        listItemGoiY0.add(ItemImageText(R.drawable.camera,"Máy ảnh","camera"))
        listItemGoiY0.add(ItemImageText(R.drawable.chuot,"Phụ kiện máy tính","mouse"))
        listItemGoiY0.add(ItemImageText(R.drawable.linhkien,"Linh kiện máy tính","accessory"))
        rvGoiYChuDe.adapter = ViewItemElecAdapter(listItemGoiY0,object: ClickInterface {
            override fun setOnClick(pos: Int) {
                val intent1 = Intent(requireContext(), ProductActivity::class.java)
                val bundle = Bundle()
                bundle.putString("category","electron")
                bundle.putString("city",txtCity.text.toString())
                bundle.putString("product",listItemGoiY0[pos].key)
                intent1.putExtras(bundle)
                startActivity(intent1)
            }
        })
        rvGoiYChuDe.layoutManager= GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        //DisplaySuggest1
        var rvGoiYElec1:RecyclerView = view.findViewById(R.id.rvGoiYElec1)
        var listGoiYElec1:MutableList<ItemProduct> = mutableListOf()
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener{result,e->
                if(e!=null) {}
                listGoiYElec1.clear()
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
                    if(document.data?.get("display").toString() == "true" && price.length == 10 && document.data?.get("username").toString() != username) {
                        listGoiYElec1.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                    }
                    if(listGoiYElec1.size == 5){break}
                }
                rvGoiYElec1.adapter = ViewItemProduct1Adapter(listGoiYElec1,object : ClickInterface {
                    override fun setOnClick(pos: Int) {
                        val i1 = Intent(context, DetailActivity::class.java)
                        i1.putExtra("id",listGoiYElec1[pos].id)
                        startActivity(i1)
                    }
                })
                rvGoiYElec1.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            }

        var LLDanhChoBan:LinearLayout = view.findViewById(R.id.LLDanhChoBan)
        LLDanhChoBan.setBackgroundResource(R.drawable.background_home2)
        var txtDanhChoBan:TextView = view.findViewById(R.id.txtDanhChoBan)
        txtDanhChoBan.setTextColor(ContextCompat.getColor(requireContext(),R.color.textColor))
        var txtMoiNhat:TextView = view.findViewById(R.id.txtMoiNhat)
        var LLMoiNhat:LinearLayout = view.findViewById(R.id.LLMoiNhat)
        var rvGoiYElec2:RecyclerView = view.findViewById(R.id.rvGoiYElec2)
        var listGoiYElec2:MutableList<ItemProduct> = mutableListOf()

        LLDanhChoBan.setOnClickListener {
            LLDanhChoBan.setBackgroundResource(R.drawable.background_home2)
            LLMoiNhat.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            txtDanhChoBan.setTextColor(ContextCompat.getColor(requireContext(),R.color.textColor))
            txtMoiNhat.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

            val db = FirebaseFirestore.getInstance()
            db.collection("products")
                .whereEqualTo("city",txtCity.text.toString())
                .whereEqualTo("display","true")
                .addSnapshotListener{result,e->
                    if(e!=null) {}
                    listGoiYElec2.clear()
                    for(document in result!!) {
                        val category = document.data?.get("category").toString()
                        val imageUrl = document.data?.get("picture") as MutableList<String>
                        var title = document.data?.get("title").toString()
                        var price = document.data?.get("price").toString()
                        var city  = document.data?.get("city").toString()
                        var  idProduct = document.data?.get("id").toString()
                        val timestamp = document.getTimestamp("timestamp")
                        var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                        val txtTimeCount = mTimeCount?.timeCount()
                        city = "$city . $txtTimeCount"
                        if(category.contains("electron") && document.data?.get("username").toString() != username){
                            listGoiYElec2.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                        }
                    }
                    rvGoiYElec2.adapter = ViewItemProduct2Adapter(listGoiYElec2,object : ClickInterface {
                        override fun setOnClick(pos: Int) {
                            val i1 = Intent(context, DetailActivity::class.java)
                            i1.putExtra("id",listGoiYElec2[pos].id)
                            startActivity(i1)
                        }
                    })
                    rvGoiYElec2.layoutManager = GridLayoutManager(
                        context,2
                    )
                }
        }

        LLMoiNhat.setOnClickListener {
            LLMoiNhat.setBackgroundResource(R.drawable.background_home2)
            LLDanhChoBan.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            txtDanhChoBan.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            txtMoiNhat.setTextColor(ContextCompat.getColor(requireContext(),R.color.textColor))

            db.collection("products")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener{result,e->
                    if(e!=null) {}
                    listGoiYElec2.clear()
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
                        if(document.data?.get("display").toString() == "true" && document.data?.get("category") == "electron/telephone" && document.data?.get("username").toString() != username) {
                            listGoiYElec2.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                        }
                    }
                    rvGoiYElec2.adapter = ViewItemProduct2Adapter(listGoiYElec2,object : ClickInterface {
                        override fun setOnClick(pos: Int) {
                            val i1 = Intent(context, DetailActivity::class.java)
                            i1.putExtra("id",listGoiYElec2[pos].id)
                            startActivity(i1)
                        }
                    })
                    rvGoiYElec2.layoutManager = GridLayoutManager(
                        context,2
                    )
                }
        }

        db.collection("products")
            .whereEqualTo("city",txtCity.text.toString())
            .whereEqualTo("display","true")
            /*.whereArrayContains("category","electron")*/
            .addSnapshotListener{result,e->
                if(e!=null) {}
                listGoiYElec2.clear()
                for(document in result!!) {
                    val category = document.data?.get("category").toString()
                    val imageUrl = document.data?.get("picture") as MutableList<String>
                    var title = document.data?.get("title").toString()
                    var price = document.data?.get("price").toString()
                    var city  = document.data?.get("city").toString()
                    var  idProduct = document.data?.get("id").toString()
                    val timestamp = document.getTimestamp("timestamp")
                    var mTimeCount = timestamp?.let { it1 -> TimeCount(it1) }
                    val txtTimeCount = mTimeCount?.timeCount()
                    city = "$city . $txtTimeCount"
                    if(category.contains("electron") && document.data?.get("username").toString() != username){
                        listGoiYElec2.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                    }
                }
                rvGoiYElec2.adapter = ViewItemProduct2Adapter(listGoiYElec2,object : ClickInterface {
                    override fun setOnClick(pos: Int) {
                        val i1 = Intent(context, DetailActivity::class.java)
                        i1.putExtra("id",listGoiYElec2[pos].id)
                        startActivity(i1)
                    }
                })
                rvGoiYElec2.layoutManager = GridLayoutManager(
                    context,2
                )
            }



        backFragmentHom.setOnClickListener{
            fragmentManager?.popBackStack()
        }
        return view
    }

    fun update(str:String,obj:String) {
        txtCity.text = str
    }
}