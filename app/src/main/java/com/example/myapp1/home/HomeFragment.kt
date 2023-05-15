package com.example.myapp1.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp1.CartActivity
import com.example.myapp1.R
import com.example.myapp1.SearchActivity
import com.example.myapp1.TimeCount
import com.example.myapp1.ViewItemProduct2Adapter
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.adapter.ViewItemAdapter0
import com.example.myapp1.home.adapter.ViewPageAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import me.relex.circleindicator.CircleIndicator3
class HomeFragment : Fragment() {
    lateinit var mActivityHome:HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        //DisplaySlider()
        val viewPage2:ViewPager2 = view.findViewById(R.id.viewpage2)
        val circleindicator:CircleIndicator3 = view.findViewById(R.id.circledicator1)
        val listItem:MutableList<Int> = mutableListOf()
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        listItem.add(R.drawable.background1)
        val adapter_ = ViewPageAdapter(listItem)
        viewPage2.adapter = adapter_
        circleindicator.setViewPager(viewPage2)

        //DisplaySuggest0()
        val rvGoiYChuDe:RecyclerView = view.findViewById(R.id.rvGoiYChuDe)
        var listItemGoiY0:MutableList<ItemImageText> = mutableListOf()

        listItemGoiY0.add(ItemImageText(R.drawable.xetot1,"Xe cộ",""))
        listItemGoiY0.add(ItemImageText(R.drawable.tulanhtot1,"Điện lạnh",""))
        listItemGoiY0.add(ItemImageText(R.drawable.dienthoaitot1,"Đồ điện tử",""))
        listItemGoiY0.add(ItemImageText(R.drawable.thoitrangtot1,"Thời trang",""))
        listItemGoiY0.add(ItemImageText(R.drawable.noithattot1,"Nội thất",""))
        listItemGoiY0.add(ItemImageText(R.drawable.vanphongphamtot1,"Văn phòng phẩm",""))
        listItemGoiY0.add(ItemImageText(R.drawable.giaitritot1,"Giải trí, Thể thao, Sở thích",""))
        listItemGoiY0.add(ItemImageText(R.drawable.myphamtot1,"Mỹ phẩm",""))
        rvGoiYChuDe.adapter = ViewItemAdapter0(listItemGoiY0,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                mActivityHome.getFragment("electron")
            }
        })
        rvGoiYChuDe.layoutManager= GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        //DisplayProduct1()
        val rvProduct:RecyclerView = view.findViewById(R.id.rvProduct)
        val listProduct1:MutableList<ItemProduct> = mutableListOf()
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener{result,e->
                if(e!=null) {}
                listProduct1.clear()
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
                    if(document.data?.get("display").toString() == "true") {
                        listProduct1.add(ItemProduct(idProduct,imageUrl[0],title,price,city))
                    }
                    if(listProduct1.size == 10){break}
                }
                rvProduct.adapter = ViewItemProduct2Adapter(listProduct1,object : ClickInterface {
                    override fun setOnClick(pos: Int) {
                        val i1 = Intent(context, DetailActivity::class.java)
                        i1.putExtra("id",listProduct1[pos].id)
                        startActivity(i1)
                    }
                })
                rvProduct.layoutManager = GridLayoutManager(
                    context,2
                )
            }

        var imgCart:ImageView = view.findViewById(R.id.imgCartHome)
        imgCart.setOnClickListener{
            val i = Intent(context,CartActivity::class.java)
            startActivity(i)
        }

        var search: ImageView = view.findViewById(R.id.searchButton)
        val searchBox: EditText = view.findViewById(R.id.searchView)
        search.setOnClickListener {
            val intent = Intent(context,SearchActivity::class.java)
            intent.putExtra("KeyWord",searchBox.text.toString())
            searchBox.setText("")
            startActivity(intent)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivityHome = requireActivity() as HomeActivity
    }
}