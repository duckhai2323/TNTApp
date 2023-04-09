package com.example.myapp1.electronic

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapp1.OnInputData
import com.example.myapp1.R
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogPhone : BottomSheetDialogFragment(), OnInputData {
    private var price:String?=null
    private var warranty:String?=null
    private var capacity:String?=null
    private var status:String?=null
    private var str:String? = null
    private var color:String?=null
    lateinit var txtphanloai:TextView
    lateinit var txtNhaSanXuatMin:TextView
    lateinit var txtNhaSanXuat:TextView
    lateinit var edtNhaSanXuat:TextView

    lateinit var txtMauSacMin:TextView
    lateinit var txtMauSac:TextView
    lateinit var edtMauSac:TextView

    lateinit var txtDungLuongMin:TextView
    lateinit var txtDungLuong:TextView
    lateinit var edtDungLuong:TextView

    lateinit var txtTinhTrangMin:TextView
    lateinit var txtTinhTrang:TextView
    lateinit var edtTinhTrang:TextView

    lateinit var txtBaoHanhMin:TextView
    lateinit var txtBaoHanh:TextView
    lateinit var edtBaoHanh:TextView

    lateinit var txtGiaMin:TextView
    lateinit var txtGia:TextView
    lateinit var edtGia:TextView
    lateinit var bottomSheetDialog:BottomSheetDialog
    override fun sendData(data: String,obj:String) {
    }

    fun updateDate (data:String,obj:String) {
        if(obj == "brand") {
            str = data
            txtNhaSanXuatMin.visibility = View.VISIBLE
            txtNhaSanXuat.text = str
            txtNhaSanXuat.visibility = View.VISIBLE
            edtNhaSanXuat.visibility = View.GONE
        }
        else if(obj == "color") {
            color = data
            txtMauSacMin.visibility = View.VISIBLE
            txtMauSac.text = color
            txtMauSac.visibility = View.VISIBLE
            edtMauSac.visibility = View.GONE
        }
        else if(obj == "status") {
            status = data
            txtTinhTrangMin.visibility = View.VISIBLE
            txtTinhTrang.text = status
            txtTinhTrang.visibility = View.VISIBLE
            edtTinhTrang.visibility = View.GONE
        }
        else if(obj == "capacity") {
            capacity = data
            txtDungLuongMin.visibility = View.VISIBLE
            txtDungLuong.text = capacity
            txtDungLuong.visibility = View.VISIBLE
            edtDungLuong.visibility = View.GONE
        }
        else if(obj == "warranty"){
            warranty = data
            txtBaoHanhMin.visibility = View.VISIBLE
            txtBaoHanh.text = warranty
            txtBaoHanh.visibility = View.VISIBLE
            edtBaoHanh.visibility = View.GONE
        }
        else if(obj == "price"){
            price = data
            txtGiaMin.visibility = View.VISIBLE
            txtGia.text = price
            txtGia.visibility = View.VISIBLE
            edtGia.visibility = View.GONE
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_dspipad_phone,null)
        var phanloai: String? = arguments?.getString("phanloai")
        txtphanloai = view.findViewById(R.id.txtPL)
        txtphanloai.text = phanloai.toString()

        txtNhaSanXuat = view.findViewById(R.id.txtNhaSanXuat)
        txtNhaSanXuatMin = view.findViewById(R.id.txtNhaSanXuatMin)
        edtNhaSanXuat = view.findViewById(R.id.edtNhaSanXuat)

        txtMauSacMin = view.findViewById(R.id.txtMauSacMin)
        txtMauSac = view.findViewById(R.id.txtMauSac)
        edtMauSac = view.findViewById(R.id.edtMauSac)

        txtDungLuongMin = view.findViewById(R.id.txtDungLuongMin)
        txtDungLuong = view.findViewById(R.id.txtDungLuong)
        edtDungLuong = view.findViewById(R.id.edtDungLuong)

        txtTinhTrangMin = view.findViewById(R.id.txtTinhTrangMin)
        txtTinhTrang = view.findViewById(R.id.txtTinhTrang)
        edtTinhTrang = view.findViewById(R.id.edtTinhTrang)

        txtBaoHanhMin = view.findViewById(R.id.txtBaoHanhMin)
        txtBaoHanh = view.findViewById(R.id.txtBaoHanh)
        edtBaoHanh = view.findViewById(R.id.edtBaoHanh)

        txtGiaMin = view.findViewById(R.id.txtGiaMin)
        txtGia = view.findViewById(R.id.txtGia)
        edtGia = view.findViewById(R.id.edtGia)

        var selectBrand:LinearLayout = view.findViewById(R.id.selectBrand)
        selectBrand.setOnClickListener {
            var listColor:MutableList<ItemImageText> = mutableListOf()
            var listBrand: MutableList<ItemBrand> = mutableListOf()
            var listCapacity: MutableList<String> = mutableListOf()
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
                        var dialogBrand: DialogSelect = DialogSelect(listBrand,listColor,listCapacity,"brand")
                        fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
                    }
                }
        }

        var selectColor:LinearLayout = view.findViewById(R.id.selectColor)
        selectColor.setOnClickListener {
            var listColor:MutableList<ItemImageText> = mutableListOf()
            var listBrand: MutableList<ItemBrand> = mutableListOf()
            var listCapacity: MutableList<String> = mutableListOf()
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
            var dialogBrand: DialogSelect = DialogSelect(listBrand,listColor,listCapacity,"color")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectStatus:LinearLayout = view.findViewById(R.id.selectStatus)
        selectStatus.setOnClickListener {
            var listColor:MutableList<ItemImageText> = mutableListOf()
            var listBrand: MutableList<ItemBrand> = mutableListOf()
            var listCapacity: MutableList<String> = mutableListOf()
            var dialogBrand: DialogSelect = DialogSelect(listBrand,listColor,listCapacity,"status")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectCapacity:LinearLayout = view.findViewById(R.id.selectCapacity)
        selectCapacity.setOnClickListener {
            var listColor:MutableList<ItemImageText> = mutableListOf()
            var listBrand: MutableList<ItemBrand> = mutableListOf()
            var listCapacity: MutableList<String> = mutableListOf()
            listCapacity.add("< 8GB")
            listCapacity.add("8GB")
            listCapacity.add("16GB")
            listCapacity.add("32GB")
            listCapacity.add("64GB")
            listCapacity.add("128GB")
            listCapacity.add("256GB")
            listCapacity.add("> 256GB")
            var dialogBrand: DialogSelect = DialogSelect(listBrand,listColor,listCapacity,"capacity")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectWarranty:LinearLayout = view.findViewById(R.id.selectWarranty)
        selectWarranty.setOnClickListener {
            var listColor:MutableList<ItemImageText> = mutableListOf()
            var listBrand: MutableList<ItemBrand> = mutableListOf()
            var listCapacity: MutableList<String> = mutableListOf()
            var dialogBrand: DialogSelect = DialogSelect(listBrand,listColor,listCapacity,"warranty")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectPrice:LinearLayout = view.findViewById(R.id.selectPrice)
        selectPrice.setOnClickListener {
            var listColor:MutableList<ItemImageText> = mutableListOf()
            var listBrand: MutableList<ItemBrand> = mutableListOf()
            var listCapacity: MutableList<String> = mutableListOf()
            var dialogBrand: DialogSelect = DialogSelect(listBrand,listColor,listCapacity,"price")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var imgClose:ImageView = view.findViewById(R.id.imgClose)
        imgClose.setOnClickListener{
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return bottomSheetDialog
    }
}