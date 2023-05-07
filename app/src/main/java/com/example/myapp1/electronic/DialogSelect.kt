package com.example.myapp1.electronic

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.*
import com.example.myapp1.api.ApiService
import com.example.myapp1.api.DiaGioiHanhChinhVN
import com.example.myapp1.api.District
import com.example.myapp1.api.FetchData
import com.example.myapp1.api.Ward
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogSelect(var key:String): BottomSheetDialogFragment() {

    var fetDataApi:FetchData = FetchData()
    var listCity:MutableList<DiaGioiHanhChinhVN> =
        fetDataApi.fetchData()
    lateinit var listDistrict: MutableList<District>
    lateinit var listWard:MutableList<Ward>
    var indexCity:Int = 0
    var indexDistrict:Int = 0

    private val db = Firebase.firestore
    lateinit var bottomSheet:BottomSheetDialog
    private var mOnInputData: OnInputData0? = null

    private var city:String = ""
    private var pant:String = ""
    private var ward:String = ""

    lateinit var api:ApiService

    private  var listBrand: MutableList<ItemBrand> = mutableListOf()
    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheet =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        when(key) {
            "brand" -> {
                addEventBrand()
            }

            "color" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform, null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn màu sắc"
                var rvColor: RecyclerView = view.findViewById(R.id.rvForm)
                rvColor.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

                var listColor:MutableList<ItemImageText> = mutableListOf()
                listColor.add(ItemImageText(R.color.gray,"Xám",""))
                listColor.add(ItemImageText(R.color.black,"Đen",""))
                listColor.add(ItemImageText(R.color.black1,"Đen bóng - JetBlack",""))
                listColor.add(ItemImageText(R.drawable.background_button1,"Trắng",""))
                listColor.add(ItemImageText(R.color.red,"Đỏ",""))
                listColor.add(ItemImageText(R.color.pink,"Hồng",""))
                listColor.add(ItemImageText(R.color.yellow,"Vàng",""))
                listColor.add(ItemImageText(R.color.silver,"Bạc",""))
                listColor.add(ItemImageText(R.color.textColor,"Tím",""))
                listColor.add(ItemImageText(R.color.gold,"Vàng Gold",""))
                listColor.add(ItemImageText(R.color.blue,"Xanh dương",""))
                listColor.add(ItemImageText(R.color.green,"Xanh lá",""))

                rvColor.adapter = ViewItemColorAdapter(listColor,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        addEventColor(listColor[pos])
                    }
                })
                var txtDismissColor:ImageView = view.findViewById(R.id.txtDismissForm)
                txtDismissColor.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
                txtCancle.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "status" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectstatus,null)
                bottomSheet.setContentView(view)
                var LLOMoi:LinearLayout = view.findViewById(R.id.LLOMoi)
                var LLOChuaSua:LinearLayout = view.findViewById(R.id.LLOChuaSua)
                var LLODaSua:LinearLayout = view.findViewById(R.id.LLODaSua)
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

                var txtCancle: TextView = view.findViewById(R.id.txtCancle)
                txtCancle.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "capacity" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn dung lượng"
                var txtDismissCapacity:ImageView = view.findViewById(R.id.txtDismissForm)
                var rvCpacity:RecyclerView = view.findViewById(R.id.rvForm)

                var listCapacity: MutableList<String> = mutableListOf()
                listCapacity.add("< 8GB")
                listCapacity.add("8GB")
                listCapacity.add("16GB")
                listCapacity.add("32GB")
                listCapacity.add("64GB")
                listCapacity.add("128GB")
                listCapacity.add("256GB")
                listCapacity.add("> 256GB")

                rvCpacity.adapter = ViewItemAdapterString(listCapacity,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        addEventCapacity(listCapacity[pos])
                    }
                })
                rvCpacity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                txtDismissCapacity.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
                txtCancle.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "warranty" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_warranty,null)
                bottomSheet.setContentView(view)
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

                var txtCancle: TextView = view.findViewById(R.id.txtCancle)
                txtCancle.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "price" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_fill_in,null)
                bottomSheet.setContentView(view)
                var edtPrice:EditText = view.findViewById(R.id.edtPrice)
                var txtSave:TextView = view.findViewById(R.id.txtSave)
                txtSave.setOnClickListener{
                    val strPrice:String = edtPrice.text.toString()
                    mOnInputData?.sendData(strPrice + "đ","price")
                    bottomSheet.dismiss()
                }
                var txtDismissPrice:ImageView = view.findViewById(R.id.txtDismissPrice)
                txtDismissPrice.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }

            "time" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Thời gian sử dụng"
                var txtDismissTime:ImageView = view.findViewById(R.id.txtDismissForm)
                var listTime: MutableList<String> = mutableListOf()
                var rvTime:RecyclerView = view.findViewById(R.id.rvForm)

                listTime.add("< 1 tháng")
                listTime.add("1 tháng")
                listTime.add("2 tháng")
                listTime.add("3 tháng")
                listTime.add("4 tháng")
                listTime.add("5 tháng")
                listTime.add("6 tháng")
                listTime.add("7 tháng")
                listTime.add("8 tháng")
                listTime.add("9 tháng")
                listTime.add("10 tháng")
                listTime.add("11 tháng")
                listTime.add("12 tháng")
                listTime.add("> 12 tháng")

                rvTime.adapter = ViewItemAdapterString(listTime,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listTime[pos],key)
                        bottomSheet.dismiss()
                    }
                })
                rvTime.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                txtDismissTime.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
                txtCancle.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "address" -> {
                addEventAdress(city, pant, ward)
            }

        }

        bottomSheet . behavior . state = BottomSheetBehavior . STATE_EXPANDED
        return bottomSheet
    }



    @SuppressLint("MissingInflatedId")
    private fun addEventAdress(city:String, pant:String, ward:String) {
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_setaddress,null)
        bottomSheet.setContentView(view)
        var selectCity:LinearLayout = view.findViewById(R.id.selectCity)
        var selectPant:LinearLayout = view.findViewById(R.id.selectPant)
        var selectPhuong:LinearLayout = view.findViewById(R.id.selectPhuong)

        var txtTinhMin:TextView = view.findViewById(R.id.txtTinhMin)
        var txtTinh:TextView = view.findViewById(R.id.txtTinh)
        var edtTinh:TextView = view.findViewById(R.id.edtTinh)

        var txtQuanMin:TextView = view.findViewById(R.id.txtQuanMin)
        var txtQuan:TextView = view.findViewById(R.id.txtQuan)
        var edtQuan:TextView = view.findViewById(R.id.edtQuan)

        var txtPhuongMin:TextView = view.findViewById(R.id.txtPhuongMin)
        var txtPhuong:TextView = view.findViewById(R.id.txtPhuong)
        var edtPhuong:TextView = view.findViewById(R.id.edtPhuong)

        if(city.isNotEmpty()) {
            txtTinhMin.visibility = View.VISIBLE
            txtTinh.text = city
            txtTinh.visibility = View.VISIBLE
            edtTinh.visibility = View.GONE
        } else {
            txtTinhMin.visibility = View.GONE
            txtTinh.visibility = View.GONE
            edtTinh.visibility = View.VISIBLE
        }

        if(pant.isNotEmpty()) {
            txtQuanMin.visibility = View.VISIBLE
            txtQuan.text = pant
            txtQuan.visibility = View.VISIBLE
            edtQuan.visibility = View.GONE
        }else {
            txtQuanMin.visibility = View.GONE
            txtQuan.visibility = View.GONE
            edtQuan.visibility = View.VISIBLE
        }

        if(ward.isNotEmpty()) {
            txtPhuongMin.visibility = View.VISIBLE
            txtPhuong.text = ward
            txtPhuong.visibility = View.VISIBLE
            edtPhuong.visibility = View.GONE
        }else {
            txtPhuongMin.visibility = View.GONE
            txtPhuong.visibility = View.GONE
            edtPhuong.visibility = View.VISIBLE
        }

        selectCity.setOnClickListener {
            addEventCity()
        }
        selectPant.setOnClickListener{
            if(city.isEmpty()) {
                addEventCity()
            } else {
                addEventPant()
            }
        }

        selectPhuong.setOnClickListener {
            if (city.isEmpty()) {
                addEventCity()
            } else if(pant.isEmpty()){
                addEventPant()
            } else {
                addEventWard()
            }
        }

        var cf:TextView = view.findViewById(R.id.confirmAddress)
        cf.setOnClickListener{
            mOnInputData?.sendData(ward + ", " + pant + ", " + city,key)
            bottomSheet.dismiss()
        }

        var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
        dismiss.setOnClickListener{
            bottomSheet.dismiss()
        }
    }

    private fun addEventCity() {
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_selectcity,null)
        bottomSheet.setContentView(view)
        var txtDismissCity:ImageView = view.findViewById(R.id.txtDismissCity)
        var rvCity:RecyclerView = view.findViewById(R.id.rvCity)
        listCity =fetDataApi.fetchData()
        var listCityName:MutableList<String> = mutableListOf()
        for(i in listCity) {
            listCityName.add(i.Name)
        }
        rvCity.adapter = ViewItemAdapterString(listCityName,object:ClickInterface {
            override fun setOnClick(pos: Int) {
                indexCity = pos
                if (listCityName[pos] != city) {
                    pant = ""
                    ward = ""
                }
                city = listCityName[pos]
                addEventPant()
            }
        })

        rvCity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        txtDismissCity.setOnClickListener{

            addEventAdress(city, pant, ward)
        }
    }

    private fun addEventPant() {
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn quận, huyện, thị xã"
        var rvPant:RecyclerView = view.findViewById(R.id.rvForm)
        listDistrict = listCity[indexCity].Districts
        var listPant:MutableList<String> = mutableListOf()
        listDistrict = listCity[indexCity].Districts
        for(i in listDistrict) {
            listPant.add(i.Name)
        }
        rvPant.adapter = ViewItemAdapterString(listPant,object:ClickInterface{
            override fun setOnClick(pos1: Int) {
                indexDistrict = pos1
                if (listPant[pos1] != pant) {
                    ward = ""
                }
                pant = listPant[pos1]
                addEventWard()
            }
        })
        rvPant.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var txtDismissPant:ImageView = view.findViewById(R.id.txtDismissForm)
        txtDismissPant.setOnClickListener{
            addEventCity()
        }

        var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
        txtCancle.visibility = View.GONE
    }

    private fun addEventWard() {
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn phường, xã, thị trấn"
        var rvWard:RecyclerView = view.findViewById(R.id.rvForm)
        listWard = listDistrict[indexDistrict].Wards
        var list:MutableList<String> = mutableListOf()
        for(i in listWard) {
            list.add(i.Name)
        }
        rvWard.adapter = ViewItemAdapterString(list,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                ward = list[pos]
                addEventAdress(city, pant, ward)
            }
        })
        rvWard.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var txtDismissPant:ImageView = view.findViewById(R.id.txtDismissForm)
        txtDismissPant.setOnClickListener{
            addEventPant()
        }

        var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
        txtCancle.visibility = View.GONE
    }

    private fun addEventCapacity(s: String) {
        var capacity:String = s
        mOnInputData?.sendData(capacity,key)
        bottomSheet.dismiss()
    }

    private fun addEventColor(itemImageText: ItemImageText) {
        var color:String = itemImageText.topic
        mOnInputData?.sendData(color,key)
        bottomSheet.dismiss()
    }

    private fun addEventBrand() {
        listBrand.clear()
        var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform, null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn hãng"
        var rvBrand: RecyclerView = view.findViewById(R.id.rvForm)
        rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val db = Firebase.firestore
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
                            addEventSeries(listBrand[pos])
                        }
                    })
                }
            }

        var txtDismiss: ImageView = view.findViewById(R.id.txtDismissForm)
        txtDismiss.setOnClickListener {
            bottomSheet.dismiss()
        }

        var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
        txtCancle.setOnClickListener {
            bottomSheet.dismiss()
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun addEventSeries(itemBrand : ItemBrand) {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn dòng máy"
        val dbRef = db.collection("category").document("electron").collection("telephone")
        var listSeries:MutableList<String> = mutableListOf()
        var rvSeries:RecyclerView = view.findViewById(R.id.rvForm)
        dbRef.whereEqualTo("name",itemBrand.name)
            .get()
            .addOnSuccessListener {it->
                if(!it.isEmpty) {
                    for(document in it.documents){
                        listSeries = document.data?.get("array") as MutableList<String>
                    }
                    rvSeries.adapter = ViewItemAdapterString(listSeries,object:ClickInterface{
                        override fun setOnClick(pos: Int) {
                            mOnInputData?.sendData(itemBrand.name+" - "+listSeries[pos],key)
                            bottomSheet.dismiss()
                        }
                    })
                }
            }
        rvSeries.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var txtBackToBrand:ImageView = view.findViewById(R.id.txtDismissForm)
        txtBackToBrand.setOnClickListener{
            addEventBrand()
        }

        var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
        txtCancle.setOnClickListener {
            bottomSheet.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnInputData = context as? OnInputData0
    }
}