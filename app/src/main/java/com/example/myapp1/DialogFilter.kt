package com.example.myapp1

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.electronic.DialogSelect
import com.example.myapp1.electronic.ItemBrand
import com.example.myapp1.electronic.ViewItemBrandAdapter
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DialogFilter(val key:String):BottomSheetDialogFragment() {
    private val db = Firebase.firestore
    lateinit var bottomSheet: BottomSheetDialog
    private var mOnInputData: OnInputData? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheet =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        lateinit var view: View
        when(key) {

            "brand" -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_selectbrand, null)
                var rvBrand: RecyclerView = view.findViewById(R.id.rvBrand)
                rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                var listBrand: MutableList<ItemBrand> = mutableListOf()
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
                                    mOnInputData?.sendData(listBrand[pos].name,key)
                                    bottomSheet.dismiss()
                                }
                            })
                        }
                    }
                var txtDismiss: ImageView = view.findViewById(R.id.txtDismiss)
                txtDismiss.setOnClickListener {
                    bottomSheet.dismiss()
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
        }
        bottomSheet.setContentView(view)
        return bottomSheet
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnInputData = context as? OnInputData
    }
}