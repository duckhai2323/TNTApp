package com.example.myapp1.electronic

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import com.example.myapp1.ViewItemAdapterImage
import com.example.myapp1.home.ItemImageText
import com.example.myapp1.home.username
import com.google.android.gms.cast.framework.media.ImagePicker
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.sql.Timestamp
import java.util.UUID

class DialogPhone() : BottomSheetDialogFragment(){
    private val IMAGE_REQUEST_CODE = 1000
    lateinit var rvImagePush:RecyclerView
    lateinit var selectImagePush:LinearLayout
    private lateinit var adapter: ViewItemAdapterImage
    var images:MutableList<Uri> = mutableListOf()

    val db = Firebase.firestore
    val ref = db.collection("products")
    val id = ref.document().id
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
    lateinit var edtPrice:EditText
    lateinit var edtTitle:EditText
    lateinit var edtDescription:EditText

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

        /*txtGiaMin = view.findViewById(R.id.txtGiaMin)
        txtGia = view.findViewById(R.id.txtGia)
        edtGia = view.findViewById(R.id.edtGia)*/
        edtPrice = view.findViewById(R.id.edtPrice)
        edtTitle = view.findViewById(R.id.edtTitle)
        edtDescription = view.findViewById(R.id.edtDescription)

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

        /*var selectPrice:LinearLayout = view.findViewById(R.id.selectPrice)
        selectPrice.setOnClickListener {
            var dialogBrand: DialogSelect = DialogSelect("price")
            fragmentManager?.let { it1 -> dialogBrand.show(it1, "aaaa") }
        }*/

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

        selectImagePush = view.findViewById(R.id.selectImagePush)
        rvImagePush  =view.findViewById(R.id.rvImagePush)
        adapter = ViewItemAdapterImage(images)
        rvImagePush.adapter = adapter
        rvImagePush.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        selectImagePush.setOnClickListener{
            selectImagesFromGallery()
            var video:LinearLayout = view.findViewById(R.id.video)
            video.setOnClickListener{
                uploadImages(images)
            }
        }

        val txtPushData:LinearLayout = view.findViewById(R.id.pushData)
        txtPushData.setOnClickListener{
            val str = txtNhaSanXuat.text.toString()
            val index = str.indexOf("-")
            val b = str.substring(0, index).trim()
            val c = str.substring(index + 1).trim()
            val db = Firebase.firestore
            val mapTelephone:Map<String,String> = hashMapOf(
                "address" to txtDiaChi.text.toString(),
                "brand" to b,
                "capacity" to txtDungLuong.text.toString(),
                "category" to "electron/telephone",
                "city" to "Hà Nội",
                "color" to txtMauSac.text.toString(),
                "description" to edtDescription.text.toString(),
                "display" to "true",
                "id" to id,
                "price" to (edtPrice.text.toString()+"đ"),
                "series" to c,
                "status" to txtTinhTrang.text.toString(),
                "time" to txtThoiGianSD.text.toString(),
                "title" to edtTitle.text.toString(),
                "username" to username,
                "warranty" to txtBaoHanh.text.toString()
             )
            ref.document(id).update(mapTelephone)
            ref.document(id).update("timestamp",FieldValue.serverTimestamp())

            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.setCancelable(false)
        return bottomSheetDialog
    }
    private fun selectImagesFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Images"), IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if (data.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    images.add(imageUri)
                }
                selectImagePush.visibility = View.GONE
                rvImagePush.visibility = View.VISIBLE
            } else {
                val imageUri = data.data!!
                selectImagePush.visibility = View.GONE
                images.add(imageUri)
                rvImagePush.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun uploadImages(images: List<Uri>) {
        val storage = Firebase.storage
        val storageRef = storage.reference

        val pictureUrls = mutableListOf<String>()
        val uploadTasks = mutableListOf<UploadTask>()
        for (image in images) {
            val imageName = UUID.randomUUID().toString() + ".jpg"
            val imageRef = storageRef.child("images/$imageName")

            val uploadTask = imageRef.putFile(image)
            uploadTasks.add(uploadTask)

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result.toString()
                    pictureUrls.add(downloadUrl)
                } else {
                    // Xử lý lỗi tải lên ảnh
                }

                if (pictureUrls.size == images.size) {
                    // Tất cả các ảnh đã được tải lên thành công
                    // Tiến hành lưu các URL vào Firestore
                    savePictureUrls(pictureUrls)
                }
            }
        }
    }

    private fun savePictureUrls(pictureUrls: List<String>) {
        val db = Firebase.firestore
        val productRef = db.collection("products").document(id)

        val data:Map<String , Any> = hashMapOf(
            "picture" to pictureUrls
        )

        productRef.set(data)
            .addOnSuccessListener {
                // Xử lý khi cập nhật thành công
            }
            .addOnFailureListener { e ->
                // Xử lý khi cập nhật thất bại
            }
    }


}
