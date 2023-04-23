package com.example.myapp1.electronic

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapp1.R
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogPhone : BottomSheetDialogFragment(){
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

    lateinit var txtThoiGianSDMin:TextView
    lateinit var txtThoiGianSD:TextView
    lateinit var edtThoiGianSD:TextView

    lateinit var txtDiaChiMin:TextView
    lateinit var txtDiaChi:TextView
    lateinit var edtDiaChi:TextView
    lateinit var bottomSheetDialog:BottomSheetDialog

    fun updateDate (data:String,key:String) {
        when(key) {
            "brand" -> {
                txtNhaSanXuatMin.visibility = View.VISIBLE
                txtNhaSanXuat.text = data
                txtNhaSanXuat.visibility = View.VISIBLE
                edtNhaSanXuat.visibility = View.GONE
            }

            "color" -> {
                txtMauSacMin.visibility = View.VISIBLE
                txtMauSac.text = data
                txtMauSac.visibility = View.VISIBLE
                edtMauSac.visibility = View.GONE
            }

            "status" -> {
                txtTinhTrangMin.visibility = View.VISIBLE
                txtTinhTrang.text = data
                txtTinhTrang.visibility = View.VISIBLE
                edtTinhTrang.visibility = View.GONE
            }

            "capacity" -> {
                txtDungLuongMin.visibility = View.VISIBLE
                txtDungLuong.text = data
                txtDungLuong.visibility = View.VISIBLE
                edtDungLuong.visibility = View.GONE
            }

            "warranty" -> {
                txtBaoHanhMin.visibility = View.VISIBLE
                txtBaoHanh.text = data
                txtBaoHanh.visibility = View.VISIBLE
                edtBaoHanh.visibility = View.GONE
            }

            "price" -> {
                txtGiaMin.visibility = View.VISIBLE
                txtGia.text = data
                txtGia.visibility = View.VISIBLE
                edtGia.visibility = View.GONE
            }

            "time" -> {
                txtThoiGianSDMin.visibility = View.VISIBLE
                txtThoiGianSD.text = data
                txtThoiGianSD.visibility =View.VISIBLE
                edtThoiGianSD.visibility = View.GONE
            }

            "address" -> {
                txtDiaChiMin.visibility = View.VISIBLE
                txtDiaChi.text = data
                txtDiaChi.visibility =View.VISIBLE
                edtDiaChi.visibility = View.GONE
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        var view:View = LayoutInflater.from(context).inflate(R.layout.layout_telephone,null)
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

        txtThoiGianSDMin = view.findViewById(R.id.txtThoiGianDaSDMin)
        txtThoiGianSD = view.findViewById(R.id.txtThoiGianDaSD)
        edtThoiGianSD = view.findViewById(R.id.edtThoiGianDaSD)

        txtDiaChiMin = view.findViewById(R.id.txtDiaChiMin)
        txtDiaChi = view.findViewById(R.id.txtDiaChi)
        edtDiaChi = view.findViewById(R.id.edtDiaChi)

        var selectBrand:LinearLayout = view.findViewById(R.id.selectBrand)
        selectBrand.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("brand")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectColor:LinearLayout = view.findViewById(R.id.selectColor)
        selectColor.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("color")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectStatus:LinearLayout = view.findViewById(R.id.selectStatus)
        selectStatus.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("status")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectCapacity:LinearLayout = view.findViewById(R.id.selectCapacity)
        selectCapacity.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("capacity")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectWarranty:LinearLayout = view.findViewById(R.id.selectWarranty)
        selectWarranty.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("warranty")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectPrice:LinearLayout = view.findViewById(R.id.selectPrice)
        selectPrice.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("price")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectAddress:LinearLayout = view.findViewById(R.id.selectAddress)
        selectAddress.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("address")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }

        var selectTime:LinearLayout = view.findViewById(R.id.selectTime)
        selectTime.setOnClickListener{
            var dialogBrand: DialogSelect = DialogSelect("time")
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