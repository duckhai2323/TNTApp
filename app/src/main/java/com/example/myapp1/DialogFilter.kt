package com.example.myapp1

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.electronic.DialogSelect
import com.example.myapp1.electronic.ItemBrand
import com.example.myapp1.electronic.ViewItemBrandAdapter
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogFilter(val key:String, val product:String):BottomSheetDialogFragment() {
    private val db = Firebase.firestore
    lateinit var bottomSheet: BottomSheetDialog
    private var mOnInputData: OnInputData? = null

    private  var listBrand: MutableList<ItemBrand> = mutableListOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheet =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        lateinit var view: View
        when(key) {

            "address" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectcity, null)
                var list = resources.getStringArray(R.array.list_city)
                var listCity:MutableList<String> = list.toMutableList()
                var rvCity:RecyclerView = view.findViewById(R.id.rvCity)
                rvCity.adapter = ViewItemAdapterString(listCity,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        var str:String = listCity[pos]
                        mOnInputData?.sendData(str,key)
                        bottomSheet.dismiss()
                    }
                })
                rvCity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                var imgDismissCity:ImageView = view.findViewById(R.id.txtDismissCity)
                imgDismissCity.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }

            "brand" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectbrand, null)
                var rvBrand: RecyclerView = view.findViewById(R.id.rvBrand)
                rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                val db = Firebase.firestore
                when(product) {
                    "telephone" -> {
                        db.collection("category").document("electron")
                            .collection("telephone")
                            .get()
                            .addOnSuccessListener {
                                if(!it.isEmpty) {
                                    for(document in it.documents)
                                    {
                                        val name = document.data?.get("name").toString()
                                        val imageUrl = document.data?.get("imageBrand").toString()
                                        listBrand.add(ItemBrand(name,imageUrl))
                                    }
                                    rvBrand.adapter = ViewItemBrandAdapter(listBrand, object : ClickInterface {
                                        override fun setOnClick(pos: Int) {
                                            mOnInputData?.sendData(listBrand[pos].name,key)
                                            bottomSheet.dismiss()
                                        }
                                    })
                                }
                            }
                    }
                }
                var txtDismiss: ImageView = view.findViewById(R.id.txtDismiss)
                txtDismiss.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "series" -> {
                if(brand_ != null) {
                    view = LayoutInflater.from(context).inflate(R.layout.layout_selectseries,null)
                    val dbRef = db.collection("category").document("electron").collection("telephone")
                    var listSeries:MutableList<String> = mutableListOf()
                    var rvSeries:RecyclerView = view.findViewById(R.id.rvSeries)
                    dbRef.whereEqualTo("name", brand_)
                        .get()
                        .addOnSuccessListener {it->
                            if(!it.isEmpty) {
                                for(document in it.documents){
                                    listSeries = document.data?.get("array") as MutableList<String>
                                }
                                rvSeries.adapter = ViewItemAdapterString(listSeries,object:ClickInterface{
                                    override fun setOnClick(pos: Int) {
                                        mOnInputData?.sendData(listSeries[pos],"series")
                                        bottomSheet.dismiss()
                                    }
                                })
                            }
                        }
                    rvSeries.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                    var txtBackToBrand:ImageView = view.findViewById(R.id.txtBackToBrand)
                    txtBackToBrand.setOnClickListener{
                        bottomSheet.dismiss()
                    }
                }
            }

            "color" -> {
                var listColor:MutableList<ItemImageText> = mutableListOf()
                listColor.add(ItemImageText(R.color.gray,"Xám"))
                listColor.add(ItemImageText(R.color.black,"Đen"))
                listColor.add(ItemImageText(R.color.black1,"Đen bóng - JetBlack"))
                listColor.add(ItemImageText(R.drawable.background_button1,"Trắng"))
                listColor.add(ItemImageText(R.color.red,"Đỏ"))
                listColor.add(ItemImageText(R.color.pink,"Hồng"))
                listColor.add(ItemImageText(R.color.yellow,"Vàng"))
                listColor.add(ItemImageText(R.color.silver,"Bạc"))
                listColor.add(ItemImageText(R.color.textColor,"Tím"))
                listColor.add(ItemImageText(R.color.gold,"Vàng Gold"))
                listColor.add(ItemImageText(R.color.blue,"Xanh dương"))
                listColor.add(ItemImageText(R.color.green,"Xanh lá"))
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectcolor, null)
                var rvColor: RecyclerView = view.findViewById(R.id.rvColor)
                rvColor.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,false)
                rvColor.adapter = ViewItemColorAdapter(listColor,object: ClickInterface {
                    override fun setOnClick(pos: Int) {
                        var color:String = listColor[pos].topic
                        mOnInputData?.sendData(color,key)
                        bottomSheet.dismiss()
                    }
                })
                var txtDismissColor: ImageView = view.findViewById(R.id.txtDismissColor)
                txtDismissColor.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }

            "status" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectstatus,null)
                var LLOMoi: LinearLayout = view.findViewById(R.id.LLOMoi)
                var LLOChuaSua: LinearLayout = view.findViewById(R.id.LLOChuaSua)
                var LLODaSua: LinearLayout = view.findViewById(R.id.LLODaSua)
                LLOMoi.setOnClickListener {
                    mOnInputData?.sendData("Mới",key)
                    bottomSheet.dismiss()
                }
                LLOChuaSua.setOnClickListener {
                    mOnInputData?.sendData("Đã sử dụng (chưa sửa chữa)",key)
                    bottomSheet.dismiss()
                }
                LLODaSua.setOnClickListener {
                    mOnInputData?.sendData("Đã sử dụng (qua sửa chữa)",key)
                    bottomSheet.dismiss()
                }
                var txtDismissStatus:ImageView = view.findViewById(R.id.txtDismissStatus)
                txtDismissStatus.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }

            "warranty" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_warranty,null)

                var LLOConBaoHanh:LinearLayout = view.findViewById(R.id.LLOConBaoHanh)
                var LLOHetBaoHanh:LinearLayout = view.findViewById(R.id.LLOHetBaoHanh)
                LLOConBaoHanh.setOnClickListener {
                    mOnInputData?.sendData("Còn bảo hành",key)
                    bottomSheet.dismiss()
                }

                LLOHetBaoHanh.setOnClickListener {
                    mOnInputData?.sendData("Hết bảo hành",key)
                    bottomSheet.dismiss()
                }

                var txtDismissWarranty:ImageView = view.findViewById(R.id.txtDismissWarranty)
                txtDismissWarranty.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }

            "capacity" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_capacity,null)
                var listCapacity: MutableList<String> = mutableListOf()
                listCapacity.add("< 8GB")
                listCapacity.add("8GB")
                listCapacity.add("16GB")
                listCapacity.add("32GB")
                listCapacity.add("64GB")
                listCapacity.add("128GB")
                listCapacity.add("256GB")
                listCapacity.add("> 256GB")
                var txtDismissCapacity:ImageView = view.findViewById(R.id.txtDismissCapacity)
                var rvCpacity:RecyclerView = view.findViewById(R.id.rvCapacity)
                rvCpacity.adapter = ViewItemAdapterString(listCapacity,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listCapacity[pos],key)
                        bottomSheet.dismiss()
                    }
                })
                rvCpacity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                txtDismissCapacity.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }
        }
        bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.setContentView(view)
        return bottomSheet
    }

    /*@SuppressLint("MissingInflatedId")
    private fun addEventBrandPhone(itemBrand : ItemBrand) {
        var view1 = LayoutInflater.from(context).inflate(R.layout.layout_selectseries,null)
        val dbRef = db.collection("category").document("electron").collection("telephone")
        var listSeries:MutableList<String> = mutableListOf()
        var rvSeries:RecyclerView = view1.findViewById(R.id.rvSeries)
        dbRef.whereEqualTo("name",itemBrand.name)
            .get()
            .addOnSuccessListener {it->
                if(!it.isEmpty) {
                    for(document in it.documents){
                        listSeries = document.data?.get("array") as MutableList<String>
                    }
                    rvSeries.adapter = ViewItemAdapterString(listSeries,object:ClickInterface{
                        override fun setOnClick(pos: Int) {
                            mOnInputData?.sendData(listSeries[pos],"series")
                            bottomSheet.dismiss()
                        }
                    })
                }
            }
        rvSeries.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        bottomSheet.setContentView(view1)

        var txtBackToBrand:ImageView = view1.findViewById(R.id.txtBackToBrand)
        txtBackToBrand.setOnClickListener{
            var view0:View = LayoutInflater.from(context).inflate(R.layout.layout_selectbrand,null)
            bottomSheet.setContentView(view0)
            var rvBrand:RecyclerView = view0.findViewById(R.id.rvBrand)
            rvBrand.adapter = ViewItemBrandAdapter(listBrand,object:ClickInterface{
                override fun setOnClick(pos: Int) {
                    mOnInputData?.sendData(listBrand[pos].name,key)
                    addEventBrandPhone(listBrand[pos])
                }
            })
            rvBrand.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

            var txtDismiss:ImageView = view0.findViewById(R.id.txtDismiss)
            txtDismiss.setOnClickListener{
                bottomSheet.dismiss()
            }
        }
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnInputData = context as? OnInputData
    }
}