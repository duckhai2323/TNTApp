package com.example.myapp1

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.electronic.DialogPhone
import com.example.myapp1.home.ClickInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogDSP(var dialogDSP: DialogPhone): BottomSheetDialogFragment() {

    lateinit var batdongsan:LinearLayout
    lateinit var xeco:LinearLayout
    lateinit var dodientu:LinearLayout
    lateinit var thucung:LinearLayout
    lateinit var tulanh:LinearLayout
    lateinit var noithat:LinearLayout
    lateinit var thoitrang:LinearLayout
    lateinit var giaitri:LinearLayout
    lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        var view: View = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1,null)
        bottomSheetDialog.setContentView(view)
        var txtBack:ImageView = view.findViewById(R.id.imgBack)
        txtBack.setOnClickListener{
            bottomSheetDialog.dismiss()
        }
        addEvent(view)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return bottomSheetDialog
    }

    private fun addEvent(view:View) {
        displayBatDongSan(view)
        displayXeCo(view)
        displayDoDienTu(view)
        displayTuLanh(view)
        displayNoiThat(view)
        displayThoiTrang(view)
        displayGiaiTri(view)
    }

    @SuppressLint("MissingInflatedId")
    private fun displayGiaiTri(view: View) {
        giaitri = view.findViewById(R.id.giaitri)
        giaitri.setOnClickListener{
            var view1:View = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc,null)
            bottomSheetDialog.setContentView(view1)

            var listDanhMuc:MutableList<String> = mutableListOf()
            listDanhMuc.add("Nhạc cụ")
            listDanhMuc.add("Sách")
            listDanhMuc.add("Đồ thể thao, Dã ngoại")
            listDanhMuc.add("Đồ sưu tầm, đồ cổ")
            listDanhMuc.add("Thiết bị chơi game")
            listDanhMuc.add("Sở thích khác")
            var rvGiaiTri:RecyclerView = view1.findViewById(R.id.rvDanhMuc)
            rvGiaiTri.adapter = ViewItemAdapterString(listDanhMuc, object:ClickInterface{
                override fun setOnClick(pos: Int) {

                }
            })
            rvGiaiTri.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)

            var txtBackToDanhMuc:ImageView = view1.findViewById(R.id.txtBackToDanhMuc)
            txtBackToDanhMuc.setOnClickListener {
                var view2:View = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1,null)
                bottomSheetDialog.setContentView(view2)
                var txtBack:ImageView = view2.findViewById(R.id.imgBack)
                txtBack.setOnClickListener{
                    bottomSheetDialog.dismiss()
                }
                addEvent(view2)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun displayThoiTrang(view: View) {
        thoitrang = view.findViewById(R.id.thoitrang)
        thoitrang.setOnClickListener{
            var view1:View = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc,null)
            bottomSheetDialog.setContentView(view1)

            var listDanhMuc:MutableList<String> = mutableListOf()
            listDanhMuc.add("Quần áo")
            listDanhMuc.add("Đồng hồ")
            listDanhMuc.add("Giày dép")
            listDanhMuc.add("Túi xách")
            listDanhMuc.add("Nước hoa")
            listDanhMuc.add("Trang sức")
            listDanhMuc.add("Phụ kiện khác")
            var rvGiaiTri:RecyclerView = view1.findViewById(R.id.rvDanhMuc)
            rvGiaiTri.adapter = ViewItemAdapterString(listDanhMuc, object:ClickInterface{
                override fun setOnClick(pos: Int) {

                }
            })
            rvGiaiTri.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)

            var txtBackToDanhMuc:ImageView = view1.findViewById(R.id.txtBackToDanhMuc)
            txtBackToDanhMuc.setOnClickListener {
                var view2:View = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1,null)
                bottomSheetDialog.setContentView(view2)
                var txtBack:ImageView = view2.findViewById(R.id.imgBack)
                txtBack.setOnClickListener{
                    bottomSheetDialog.dismiss()
                }
                addEvent(view2)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun displayNoiThat(view: View) {
        noithat = view.findViewById(R.id.noithat)
        noithat.setOnClickListener{
            var view1:View = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc,null)
            bottomSheetDialog.setContentView(view1)

            var listDanhMuc:MutableList<String> = mutableListOf()
            listDanhMuc.add("Bàn ghế")
            listDanhMuc.add("Tủ, kệ gia đình")
            listDanhMuc.add("Giường, chăn ga gối đệm")
            listDanhMuc.add("Bếp, lò, đồ điện gia dụng")
            listDanhMuc.add("Dụng cụ nhà bếp")
            listDanhMuc.add("Cây cảnh, đồ trang trí")
            listDanhMuc.add("Thiết bị vệ sinh, nhà tắm")
            listDanhMuc.add("Nội thất, đồ gia dụng khác")

            var rvGiaiTri:RecyclerView = view1.findViewById(R.id.rvDanhMuc)
            rvGiaiTri.adapter = ViewItemAdapterString(listDanhMuc, object:ClickInterface{
                override fun setOnClick(pos: Int) {

                }
            })
            rvGiaiTri.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)

            var txtBackToDanhMuc:ImageView = view1.findViewById(R.id.txtBackToDanhMuc)
            txtBackToDanhMuc.setOnClickListener {
                var view2:View = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1,null)
                bottomSheetDialog.setContentView(view2)
                var txtBack:ImageView = view2.findViewById(R.id.imgBack)
                txtBack.setOnClickListener{
                    bottomSheetDialog.dismiss()
                }
                addEvent(view2)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun displayTuLanh(view: View) {
        tulanh = view.findViewById(R.id.tulanh)
        tulanh.setOnClickListener{
            var view1:View = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc,null)
            bottomSheetDialog.setContentView(view1)

            var listDanhMuc:MutableList<String> = mutableListOf()
            listDanhMuc.add("Tủ lạnh")
            listDanhMuc.add("Điều hòa, Máy lạnh")
            listDanhMuc.add("Máy giặt")
            var rvGiaiTri:RecyclerView = view1.findViewById(R.id.rvDanhMuc)
            rvGiaiTri.adapter = ViewItemAdapterString(listDanhMuc, object:ClickInterface{
                override fun setOnClick(pos: Int) {

                }
            })
            rvGiaiTri.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)

            var txtBackToDanhMuc:ImageView = view1.findViewById(R.id.txtBackToDanhMuc)
            txtBackToDanhMuc.setOnClickListener {
                var view2:View = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1,null)
                bottomSheetDialog.setContentView(view2)
                var txtBack:ImageView = view2.findViewById(R.id.imgBack)
                txtBack.setOnClickListener{
                    bottomSheetDialog.dismiss()
                }
                addEvent(view2)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun displayDoDienTu(view: View) {
        dodientu = view.findViewById(R.id.dodientu)
        dodientu.setOnClickListener{
            var view1:View = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc,null)
            bottomSheetDialog.setContentView(view1)

            var listDanhMuc:MutableList<String> = mutableListOf()
            listDanhMuc.add("Điện thoại")
            listDanhMuc.add("Lap top")
            listDanhMuc.add("Máy tính bảng")
            listDanhMuc.add("Máy tính để bàn")
            listDanhMuc.add("Máy ảnh, Máy quay")
            listDanhMuc.add("Tivi, Âm thanh")
            listDanhMuc.add("Thiết bị đeo thông minh")
            listDanhMuc.add("Phụ kiện (Màn hình, chuột...")
            listDanhMuc.add("Linh kiện (RAM,Card...)")

            var rvGiaiTri:RecyclerView = view1.findViewById(R.id.rvDanhMuc)
            rvGiaiTri.adapter = ViewItemAdapterString(listDanhMuc, object:ClickInterface{
                override fun setOnClick(pos: Int) {
                    bottomSheetDialog.dismiss()
                    if(pos == 0) {
                        fragmentManager?.let { it1 -> dialogDSP.show(it1,"aaaa") }
                        var phanloai:String = "Đồ điện tử - " + listDanhMuc[pos]
                        var bundle = Bundle()
                        bundle.putString("phanloai",phanloai)
                        dialogDSP.isCancelable = false
                        dialogDSP.arguments = bundle
                    }
                }
            })
            rvGiaiTri.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)

            var txtBackToDanhMuc:ImageView = view1.findViewById(R.id.txtBackToDanhMuc)
            txtBackToDanhMuc.setOnClickListener {
                var view2:View = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1,null)
                bottomSheetDialog.setContentView(view2)
                var txtBack:ImageView = view2.findViewById(R.id.imgBack)
                txtBack.setOnClickListener{
                    bottomSheetDialog.dismiss()
                }
                addEvent(view2)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun displayXeCo(view:View) {
        xeco = view.findViewById(R.id.xeco)
        xeco.setOnClickListener{
            var view1:View = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc,null)
            bottomSheetDialog.setContentView(view1)

            var listDanhMuc:MutableList<String> = mutableListOf()
            listDanhMuc.add("Ô tô")
            listDanhMuc.add("Xe máy")
            listDanhMuc.add("Xe tải, xe ben")
            listDanhMuc.add("Xe điện")
            listDanhMuc.add("Xe đạp")
            listDanhMuc.add("Phương tiện khác")
            listDanhMuc.add("Phụ tùng xe")
            var rvGiaiTri:RecyclerView = view1.findViewById(R.id.rvDanhMuc)
            rvGiaiTri.adapter = ViewItemAdapterString(listDanhMuc, object:ClickInterface{
                override fun setOnClick(pos: Int) {

                }
            })
            rvGiaiTri.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)

            var txtBackToDanhMuc:ImageView = view1.findViewById(R.id.txtBackToDanhMuc)
            txtBackToDanhMuc.setOnClickListener {
                var view2:View = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1,null)
                bottomSheetDialog.setContentView(view2)
                var txtBack:ImageView = view2.findViewById(R.id.imgBack)
                txtBack.setOnClickListener{
                    bottomSheetDialog.dismiss()
                }
                addEvent(view2)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun displayBatDongSan(view:View) {
        batdongsan = view.findViewById(R.id.batdongsan)
        batdongsan.setOnClickListener {
            var view1: View = LayoutInflater.from(context).inflate(R.layout.layout_danhmuc, null)
            bottomSheetDialog.setContentView(view1)

            var listDanhMuc: MutableList<String> = mutableListOf()
            listDanhMuc.add("Căn hộ/Chung cư")
            listDanhMuc.add("Nhà ở")
            listDanhMuc.add("Đất")
            listDanhMuc.add("Văn phòng, Mặt bằng kinh doanh")
            listDanhMuc.add("Phòng trọ")
            var rvGiaiTri: RecyclerView = view1.findViewById(R.id.rvDanhMuc)
            rvGiaiTri.adapter = ViewItemAdapterString(listDanhMuc, object : ClickInterface {
                override fun setOnClick(pos: Int) {

                }
            })
            rvGiaiTri.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )

            var txtBackToDanhMuc: ImageView = view1.findViewById(R.id.txtBackToDanhMuc)
            txtBackToDanhMuc.setOnClickListener {
                var view2: View =
                    LayoutInflater.from(context).inflate(R.layout.layout_bottomsheetdangsp1, null)
                bottomSheetDialog.setContentView(view2)
                var txtBack: ImageView = view2.findViewById(R.id.imgBack)
                txtBack.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                addEvent(view2)
            }
        }
    }

}