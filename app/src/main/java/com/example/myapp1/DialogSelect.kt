package com.example.myapp1

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
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogSelect(var listBrand:MutableList<ItemBrand>,var listColor:MutableList<ItemImageText>,var listCapacity:MutableList<String>,var key:String): BottomSheetDialogFragment() {
    private val db = Firebase.firestore
    lateinit var bottomSheet:BottomSheetDialog
    interface OnInputData{
        fun sendData(str:String,obj:String)
    }
    private var mOnInputData: OnInputData? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheet =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        lateinit var view:View
        when(key) {
            "brand" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectbrand, null)
                var rvBrand: RecyclerView = view.findViewById(R.id.rvBrand)
                rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rvBrand.adapter = ViewItemBrandAdapter(listBrand, object : ClickInterface {
                    override fun setOnClick(pos: Int) {
                        addEventBrand(listBrand[pos])
                    }
                })

                var txtDismiss: ImageView = view.findViewById(R.id.txtDismiss)
                txtDismiss.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

            "color" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectcolor, null)
                var rvColor: RecyclerView = view.findViewById(R.id.rvColor)
                rvColor.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvColor.adapter = ViewItemColorAdapter(listColor,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        addEventColor(listColor[pos])
                    }
                })
                var txtDismissColor:ImageView = view.findViewById(R.id.txtDismissColor)
                txtDismissColor.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }

            "status" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectstatus,null)
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
            }

            "capacity" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_capacity,null)
                var txtDismissCapacity:ImageView = view.findViewById(R.id.txtDismissCapacity)
                var rvCpacity:RecyclerView = view.findViewById(R.id.rvCapacity)
                rvCpacity.adapter = ViewItemAdapterString(listCapacity,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        addEventCapacity(listCapacity[pos])
                    }
                })
                rvCpacity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                txtDismissCapacity.setOnClickListener{
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

            "price" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_fill_in,null)
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

        }
        bottomSheet.setContentView(view)
        bottomSheet . behavior . state = BottomSheetBehavior . STATE_EXPANDED
        return bottomSheet
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

       var txtBackToBrand:ImageView = view1.findViewById(R.id.txtBackToBrand)
        txtBackToBrand.setOnClickListener{
            var view0:View = LayoutInflater.from(context).inflate(R.layout.layout_selectbrand,null)
            bottomSheet.setContentView(view0)
            var rvBrand:RecyclerView = view0.findViewById(R.id.rvBrand)
            rvBrand.adapter = ViewItemBrandAdapter(listBrand,object:ClickInterface{
                override fun setOnClick(pos: Int) {
                    addEventBrand(listBrand[pos])
                }
            })
            rvBrand.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

            var txtDismiss:ImageView = view0.findViewById(R.id.txtDismiss)
            txtDismiss.setOnClickListener{
                bottomSheet.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnInputData = context as? OnInputData
    }
}