package com.example.myapp1

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.electronic.ItemBrand
import com.example.myapp1.electronic.ViewItemBrandAdapter
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogFilter(val key:String):BottomSheetDialogFragment() {
    private val db = Firebase.firestore
    lateinit var bottomSheet: BottomSheetDialog
    private var mOnInputData: OnInputData1? = null

    private  var listBrand: MutableList<ItemBrand> = mutableListOf()

    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheet =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        when(key) {
            "city" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectcity, null)
                bottomSheet.setContentView(view)
                var list = resources.getStringArray(R.array.list_city)
                var listCity:MutableList<String> = list.toMutableList()
                var rvCity:RecyclerView = view.findViewById(R.id.rvCity)
                rvCity.adapter = ViewItemAdapterString(listCity,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        var str:String = listCity[pos]
                        mOnInputData?.sendData(str,key,"accept")
                        bottomSheet.dismiss()
                    }
                })
                rvCity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                var imgDismissCity:ImageView = view.findViewById(R.id.txtDismissCity)
                imgDismissCity.setOnClickListener{
                    bottomSheet.dismiss()
                }
            }

            "electron" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc, null)
                bottomSheet.setContentView(view)
                var listDanhMuc:MutableList<String> = mutableListOf()
                listDanhMuc.add("Điện thoại")
                listDanhMuc.add("Laptop")
                listDanhMuc.add("Máy tính bảng")
                listDanhMuc.add("Máy tính để bàn")
                listDanhMuc.add("Máy ảnh, Máy quay")
                listDanhMuc.add("Tivi, Âm thanh")
                listDanhMuc.add("Thiết bị đeo thông minh")
                listDanhMuc.add("Phụ kiện (Màn hình, chuột...")
                listDanhMuc.add("Linh kiện (RAM,Card...)")

                var rvDanhMuc:RecyclerView = view.findViewById(R.id.rvDanhMuc)
                rvDanhMuc.adapter = ViewItemAdapterString(listDanhMuc, object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listDanhMuc[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var imgBack:ImageView = view.findViewById(R.id.txtBackToDanhMuc)
                imgBack.setOnClickListener{
                    bottomSheet.dismiss()
                }
                rvDanhMuc.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,false)
            }

            "brand" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform ,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn hãng"
                var rvBrand: RecyclerView = view.findViewById(R.id.rvForm)
                rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                val db = Firebase.firestore
                db.collection("category").document(category)
                    .collection(product)
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
                                    brand_ = listBrand[pos].name
                                    mOnInputData?.sendData(listBrand[pos].name,key,"accept")
                                    bottomSheet.dismiss()
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
                    brand_ = ""
                    mOnInputData?.sendData("Hãng",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "series" -> {
                if(brand_.isNotEmpty()) {
                    var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                    bottomSheet.setContentView(view)
                    var txtForm:TextView = view.findViewById(R.id.txtForm)
                    txtForm.text = "Chọn dòng máy"
                    val dbRef = db.collection("category").document(category).collection(product)
                    var listSeries:MutableList<String> = mutableListOf()
                    var rvSeries:RecyclerView = view.findViewById(R.id.rvForm)
                    dbRef.whereEqualTo("name", brand_)
                        .get()
                        .addOnSuccessListener {it->
                            if(!it.isEmpty) {
                                for(document in it.documents){
                                    listSeries = document.data?.get("array") as MutableList<String>
                                }
                                rvSeries.adapter = ViewItemAdapterString(listSeries,object:ClickInterface{
                                    override fun setOnClick(pos: Int) {
                                        mOnInputData?.sendData(listSeries[pos],"series","accept")
                                        bottomSheet.dismiss()
                                    }
                                })
                            }
                        }
                    rvSeries.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                    var txtBackToBrand:ImageView = view.findViewById(R.id.txtDismissForm)
                    txtBackToBrand.setOnClickListener{
                        bottomSheet.dismiss()
                    }

                    var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
                    txtCancle.setOnClickListener {
                        mOnInputData?.sendData("Dòng máy",key,"cancle")
                        bottomSheet.dismiss()
                    }
                } else {
                    addEventBrand()
                }
            }

            "color" -> {
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
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform, null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn màu sắc"
                var rvColor: RecyclerView = view.findViewById(R.id.rvForm)
                rvColor.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,false)
                rvColor.adapter = ViewItemColorAdapter(listColor,object: ClickInterface {
                    override fun setOnClick(pos: Int) {
                        var color:String = listColor[pos].topic
                        mOnInputData?.sendData(color,key,"accept")
                        bottomSheet.dismiss()
                    }
                })
                var txtDismissColor: ImageView = view.findViewById(R.id.txtDismissForm)
                txtDismissColor.setOnClickListener{
                    bottomSheet.dismiss()
                }
                var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
                txtCancle.setOnClickListener {
                    mOnInputData?.sendData("Màu sắc",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "status" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectstatus,null)
                bottomSheet.setContentView(view)
                var LLOMoi: LinearLayout = view.findViewById(R.id.LLOMoi)
                var LLOChuaSua: LinearLayout = view.findViewById(R.id.LLOChuaSua)
                var LLODaSua: LinearLayout = view.findViewById(R.id.LLODaSua)
                LLOMoi.setOnClickListener {
                    mOnInputData?.sendData("Mới",key,"accept")
                    bottomSheet.dismiss()
                }
                LLOChuaSua.setOnClickListener {
                    mOnInputData?.sendData("Đã sử dụng (chưa sửa chữa)",key,"accept")
                    bottomSheet.dismiss()
                }
                LLODaSua.setOnClickListener {
                    mOnInputData?.sendData("Đã sử dụng (qua sửa chữa)",key,"accept")
                    bottomSheet.dismiss()
                }
                var txtDismissStatus:ImageView = view.findViewById(R.id.txtDismissStatus)
                txtDismissStatus.setOnClickListener{
                    bottomSheet.dismiss()
                }
                var txtCancle: TextView = view.findViewById(R.id.txtCancle)
                txtCancle.setOnClickListener {
                    mOnInputData?.sendData("Tình trạng",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "warranty" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_warranty,null)
                bottomSheet.setContentView(view)
                var LLOConBaoHanh:LinearLayout = view.findViewById(R.id.LLOConBaoHanh)
                var LLOHetBaoHanh:LinearLayout = view.findViewById(R.id.LLOHetBaoHanh)
                LLOConBaoHanh.setOnClickListener {
                    mOnInputData?.sendData("Còn bảo hành",key,"accept")
                    bottomSheet.dismiss()
                }

                LLOHetBaoHanh.setOnClickListener {
                    mOnInputData?.sendData("Hết bảo hành",key,"accept")
                    bottomSheet.dismiss()
                }

                var txtDismissWarranty:ImageView = view.findViewById(R.id.txtDismissWarranty)
                txtDismissWarranty.setOnClickListener{
                    bottomSheet.dismiss()
                }
                var txtCancle: TextView = view.findViewById(R.id.txtCancle)
                txtCancle.setOnClickListener {
                    mOnInputData?.sendData("Bào hành",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "capacity" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn dung lượng"
                var listCapacity: MutableList<String> = mutableListOf()
                listCapacity.add("< 8GB")
                listCapacity.add("8GB")
                listCapacity.add("16GB")
                listCapacity.add("32GB")
                listCapacity.add("64GB")
                listCapacity.add("128GB")
                listCapacity.add("256GB")
                listCapacity.add("> 256GB")
                var rvCpacity:RecyclerView = view.findViewById(R.id.rvForm)
                rvCpacity.adapter = ViewItemAdapterString(listCapacity,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listCapacity[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })
                rvCpacity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                var txtDismissCapacity:ImageView = view.findViewById(R.id.txtDismissForm)
                txtDismissCapacity.setOnClickListener{
                    bottomSheet.dismiss()
                }
                var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
                txtCancle.setOnClickListener {
                    mOnInputData?.sendData("Dung lượng",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "size" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Kích cỡ màn hình"
                var listSize:MutableList<String> = mutableListOf()
                listSize.add("< 9 inch")
                listSize.add("9 - 10.9 inch")
                listSize.add("11 - 12.9 inch")
                listSize.add("13 - 14.9 inch")
                listSize.add("15 - 16.9 inch")
                listSize.add("17 - 18.9 inch")
                listSize.add("19 - 20.9 inch")
                listSize.add("> 21 inch")

                var rvForm:RecyclerView = view.findViewById(R.id.rvForm)
                rvForm.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvForm.adapter = ViewItemAdapterString(listSize,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listSize[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCancleForm)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("Kích thước màn hình",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "ram" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn RAM"
                var listRam:MutableList<String> = mutableListOf()
                listRam.add("<4 GB")
                listRam.add("4 GB")
                listRam.add("8 GB")
                listRam.add("16 GB")
                listRam.add("32 GB")
                listRam.add("64 GB")
                listRam.add("> 64 GB")

                var rvForm:RecyclerView = view.findViewById(R.id.rvForm)
                rvForm.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvForm.adapter = ViewItemAdapterString(listRam,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listRam[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCancleForm)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("RAM",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "ssd" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn Ổ cứng"
                var listSSD:MutableList<String> = mutableListOf()
                listSSD.add("128 GB")
                listSSD.add("250 GB")
                listSSD.add("256 GB")
                listSSD.add("320 GB")
                listSSD.add("480 GB")
                listSSD.add("500 GB")
                listSSD.add("512 GB")
                listSSD.add("640 GB")
                listSSD.add("700 GB")
                listSSD.add("750 GB")
                listSSD.add("1 TB")
                listSSD.add("> 1 TB")

                var rvForm:RecyclerView = view.findViewById(R.id.rvForm)
                rvForm.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvForm.adapter = ViewItemAdapterString(listSSD,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listSSD[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCancleForm)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("Ổ cứng",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "card" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn card màn hình"
                var listCard:MutableList<String> = mutableListOf()
                listCard.add("Onboard")
                listCard.add("AMD")
                listCard.add("NVIDIA")
                listCard.add("Khác")

                var rvForm:RecyclerView = view.findViewById(R.id.rvForm)
                rvForm.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvForm.adapter = ViewItemAdapterString(listCard,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listCard[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCancleForm)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("Card màn hình",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "ship" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Vận chuyển giao nhận"
                var listShip:MutableList<String> = mutableListOf()
                listShip.add("Tự giao hàng")
                listShip.add("Dùng dịch vụ giao hàng")
                listShip.add("Tự giao hoặc đặt dịch vụ giao hàng")
                listShip.add("Không giao hàng")
                var rvForm:RecyclerView = view.findViewById(R.id.rvForm)
                rvForm.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvForm.adapter = ViewItemAdapterString(listShip,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listShip[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCancleForm)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("Vận chuyển giao nhận",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "handle" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Chọn bộ xử lí"
                var listHandle:MutableList<String> = mutableListOf()
                listHandle.add("AMG")
                listHandle.add("Athlon")
                listHandle.add("Intel Atom")
                listHandle.add("Intel Celeron")
                listHandle.add("Intel Core 2 Duo")
                listHandle.add("Intel Core 2 Quad")
                listHandle.add("Intel Core i3")
                listHandle.add("Intel Core i5")
                listHandle.add("Intel Core i7")
                listHandle.add("Intel Core i9")
                listHandle.add("Intel Pentium")
                listHandle.add("Intel Quark")
                listHandle.add("Intel Xeon")
                listHandle.add("Ryzen 3")
                listHandle.add("Ryzen 5")
                listHandle.add("Ryzen 7")
                listHandle.add("Ryzen 9")
                listHandle.add("Khác")
                var rvForm:RecyclerView = view.findViewById(R.id.rvForm)
                rvForm.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvForm.adapter = ViewItemAdapterString(listHandle,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listHandle[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCancleForm)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("Bộ vi xử lí",key,"cancle")
                    bottomSheet.dismiss()
                }
            }

            "price" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectprice,null)
                bottomSheet.setContentView(view)
                var fromPrice:TextView = view.findViewById(R.id.fromPrice)
                var toPrice:TextView = view.findViewById(R.id.toPrice)
                var txtd1:TextView = view.findViewById(R.id.txtd1)
                var txtd2:TextView = view.findViewById(R.id.txtd2)
                txtd1.paintFlags = txtd1.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                txtd2.paintFlags = txtd2.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                var rangeSlider:RangeSlider = view.findViewById(R.id.rangeSlider)
                rangeSlider.stepSize = 0.1F
                rangeSlider.addOnChangeListener{slider,values,fromUser ->
                    val values = rangeSlider.values
                    if(0<values[0] && values[0]<1){
                        val temp = values[0]*10
                        fromPrice.text = temp.toString().get(0) + "00.000"
                    } else if(values[0]>=1) fromPrice.text = values[0].toString() + "00.000"
                    toPrice.text = values[1].toString() + "00.000"
                }

                var dismiss:ImageView = view.findViewById(R.id.txtDismissPrice)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCanclePrice)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("Giá",key,"cancle")
                    bottomSheet.dismiss()
                }

                var confirm:TextView = view.findViewById(R.id.confirmPrice)
                confirm.setOnClickListener{
                    mOnInputData?.sendData(fromPrice.text.toString() + " - " + toPrice.text.toString(),key,"accept")
                    bottomSheet.dismiss()
                }
            }

            "time" -> {
                var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
                bottomSheet.setContentView(view)
                var txtForm:TextView = view.findViewById(R.id.txtForm)
                txtForm.text = "Thời gian đã sử dụng"
                var listTime:MutableList<String> = mutableListOf()
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

                var rvForm:RecyclerView = view.findViewById(R.id.rvForm)
                rvForm.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                rvForm.adapter = ViewItemAdapterString(listTime,object:ClickInterface{
                    override fun setOnClick(pos: Int) {
                        mOnInputData?.sendData(listTime[pos],key,"accept")
                        bottomSheet.dismiss()
                    }
                })

                var dismiss:ImageView = view.findViewById(R.id.txtDismissForm)
                dismiss.setOnClickListener{
                    bottomSheet.dismiss()
                }

                var cancle:TextView = view.findViewById(R.id.txtCancleForm)
                cancle.setOnClickListener{
                    mOnInputData?.sendData("Thời gian sử dụng",key,"cancle")
                    bottomSheet.dismiss()
                }
            }
        }
        bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return bottomSheet
    }

    private fun addEventBrand() {
        listBrand.clear()
        var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform ,null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn hãng"
        var rvBrand: RecyclerView = view.findViewById(R.id.rvForm)
        rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val db = Firebase.firestore
        db.collection("category").document(category)
            .collection(product)
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
                            brand_ = listBrand[pos].name
                            mOnInputData?.sendData(listBrand[pos].name,"brand","accept")
                            addEventSeries(listBrand[pos].name)
                        }
                    })
                }
            }
        var txtDismiss: ImageView = view.findViewById(R.id.txtDismissForm)
        txtDismiss.setOnClickListener {
            brand_ = ""
            mOnInputData?.sendData("Hãng","brand","cancle")
            bottomSheet.dismiss()
        }
        var txtCancle: TextView = view.findViewById(R.id.txtCancleForm)
        txtCancle.visibility = View.GONE
    }

    @SuppressLint("MissingInflatedId")
   private fun addEventSeries(brand:String) {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_selectform,null)
        bottomSheet.setContentView(view)
        var txtForm:TextView = view.findViewById(R.id.txtForm)
        txtForm.text = "Chọn dòng máy"
       val dbRef = db.collection("category").document(category).collection(product)
       var listSeries:MutableList<String> = mutableListOf()
       var rvSeries:RecyclerView = view.findViewById(R.id.rvForm)
       dbRef.whereEqualTo("name",brand)
           .get()
           .addOnSuccessListener {it->
               if(!it.isEmpty) {
                   for(document in it.documents){
                       listSeries = document.data?.get("array") as MutableList<String>
                   }
                   rvSeries.adapter = ViewItemAdapterString(listSeries,object:ClickInterface{
                       override fun setOnClick(pos: Int) {
                           mOnInputData?.sendData(listSeries[pos],"series","accept")
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
            brand_ = ""
            mOnInputData?.sendData("Hãng","brand","cancle")
            mOnInputData?.sendData("Dòng máy",key,"cancle")
            bottomSheet.dismiss()
        }
   }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnInputData = context as? OnInputData1
    }
}