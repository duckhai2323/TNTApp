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
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogSelect(var listBrand:MutableList<ItemBrand>, var listColor:MutableList<ItemImageText>, var listCapacity:MutableList<String>, var key:String): BottomSheetDialogFragment() {
    private val db = Firebase.firestore
    lateinit var bottomSheet:BottomSheetDialog
    private var mOnInputData: OnInputData0? = null

    private var city:String = ""
    private var pant:String = ""
    private var ward:String = ""
    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheet =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        lateinit var view:View
        when(key) {
            "brand" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectform, null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn hãng"
                var rvBrand: RecyclerView = view.findViewById(R.id.rvForm)
                rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rvBrand.adapter = ViewItemBrandAdapter(listBrand, object : ClickInterface {
                    override fun setOnClick(pos: Int) {
                        addEventBrand(listBrand[pos])
                    }
                })

                var txtDismiss: ImageView = view.findViewById(R.id.txtDismissForm)
                txtDismiss.setOnClickListener {
                    bottomSheet.dismiss()
                }

                var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
                txtCancle.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "color" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectform, null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn màu sắc"
                var rvColor: RecyclerView = view.findViewById(R.id.rvForm)
                rvColor.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
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
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectstatus,null)
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
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn dung lượng"
                var txtDismissCapacity:ImageView = view.findViewById(R.id.txtDismissForm)
                var rvCpacity:RecyclerView = view.findViewById(R.id.rvForm)
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
                view = LayoutInflater.from(context).inflate(R.layout.layout_warranty,null)
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
                view = LayoutInflater.from(context).inflate(R.layout.layout_fill_in,null)
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
                addEventPant(city)
            }
        }

        selectPhuong.setOnClickListener {
            if (city.isEmpty()) {
                addEventCity()
            } else if(pant.isEmpty()){
                addEventPant(city)
            } else {
                addEventWard(city, pant)
            }
        }

        var cf:TextView = view.findViewById(R.id.confirmAddress)
        cf.setOnClickListener{
            mOnInputData?.sendData(ward + ", " + pant + ", " + city,key)
            bottomSheet.dismiss()
        }
    }

    private fun addEventCity() {
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_selectcity,null)
        bottomSheet.setContentView(view)
        var txtDismissCity:ImageView = view.findViewById(R.id.txtDismissCity)
        var list = resources.getStringArray(R.array.list_city)
        var listCity:MutableList<String> = list.toMutableList()
        var rvCity:RecyclerView = view.findViewById(R.id.rvCity)
        rvCity.adapter = ViewItemAdapterString(listCity,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                if(listCity[pos]!=city) {
                    pant = ""
                    ward = ""
                }
                city = listCity[pos]
                addEventPant(city)
            }
        })
        rvCity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        txtDismissCity.setOnClickListener{
            addEventAdress(city, pant, ward)
        }
    }

    private fun addEventPant(city:String) {
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn quận, huyện, thị xã"
        var rvCity:RecyclerView = view.findViewById(R.id.rvForm)
        var listPant:MutableList<String> = mutableListOf()
        val dbRef = db.collection("city")
            .document(city)
            .get()
            .addOnSuccessListener{
                if(it.exists()) {
                    listPant = it.data?.get("array") as MutableList<String>
                }
                rvCity.adapter = ViewItemAdapterString(listPant,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        if(listPant[pos]!=pant) {
                            ward = ""
                        }
                        pant = listPant[pos]
                        addEventWard(city,listPant[pos])
                    }
                })
                rvCity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
        var txtDismissPant:ImageView = view.findViewById(R.id.txtDismissForm)
        txtDismissPant.setOnClickListener{
            addEventCity()
        }

        var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
        txtCancle.setOnClickListener {
            addEventAdress(city, pant, ward)
        }
    }

    private fun addEventWard(city:String,pant: String) {
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn phường, xã, thị trấn"
        var rvCity:RecyclerView = view.findViewById(R.id.rvForm)
        var listWard:MutableList<String> = mutableListOf()
        val dbRef = db.collection("city")
            .document(city)
            .collection("ward")
            .document(pant)
            .get()
            .addOnSuccessListener{
                if(it.exists()) {
                    listWard = it.data?.get("array") as MutableList<String>
                }
                rvCity.adapter = ViewItemAdapterString(listWard,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        ward = listWard[pos]
                        addEventAdress(city, pant, ward)
                    }
                })
                rvCity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
        var txtDismissPant:ImageView = view.findViewById(R.id.txtDismissForm)
        txtDismissPant.setOnClickListener{
            addEventPant(city)
        }

        var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
        txtCancle.setOnClickListener {
            addEventAdress(city, pant, ward)
        }
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

    @SuppressLint("MissingInflatedId")
    private fun addEventBrand(itemBrand : ItemBrand) {
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
                            var brandStr:String = itemBrand.name + " - " + listSeries[pos]
                            mOnInputData?.sendData(brandStr,key)
                            bottomSheet.dismiss()
                        }
                    })
                }
            }
        rvSeries.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        bottomSheet.setContentView(view1)

        var txtCancle: TextView = view1.findViewById(R.id.txtCancle)
        txtCancle.setOnClickListener {
            bottomSheet.dismiss()
        }

       var txtBackToBrand:ImageView = view1.findViewById(R.id.txtBackToBrand)
        txtBackToBrand.setOnClickListener{
            var view0:View = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
            var txtForm:TextView = view0.findViewById(R.id.txtForm)
            txtForm.text = "Chọn hãng"
            bottomSheet.setContentView(view0)
            var rvBrand:RecyclerView = view0.findViewById(R.id.rvForm)
            rvBrand.adapter = ViewItemBrandAdapter(listBrand,object:ClickInterface{
                override fun setOnClick(pos: Int) {
                    addEventBrand(listBrand[pos])
                }
            })
            rvBrand.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

            var txtDismiss:ImageView = view0.findViewById(R.id.txtDismissForm)
            txtDismiss.setOnClickListener{
                bottomSheet.dismiss()
            }

            var txtCancle: TextView = view0.findViewById(R.id.txtCancleForm)
            txtCancle.setOnClickListener {
                bottomSheet.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnInputData = context as? OnInputData0
    }
}