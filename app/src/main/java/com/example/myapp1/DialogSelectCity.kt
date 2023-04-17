package com.example.myapp1

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogSelectCity : BottomSheetDialogFragment() {
    lateinit var bottomSheet:BottomSheetDialog
    private var mOnInputData: OnInputData0? = null

    @SuppressLint("ResourceType", "MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
         var view: View = LayoutInflater.from(context).inflate(R.layout.layout_selectcity,null)
        bottomSheet.setContentView(view)
        var list = resources.getStringArray(R.array.list_city)
        var listCity:MutableList<String> = list.toMutableList()
        var rvCity:RecyclerView = view.findViewById(R.id.rvCity)
        rvCity.adapter = ViewItemAdapterString(listCity,object:ClickInterface{
            override fun setOnClick(pos: Int) {
                var str:String = listCity[pos]
                mOnInputData?.sendData(str,"city")
                bottomSheet.dismiss()
            }
        })
        rvCity.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var imgDismissCity:ImageView = view.findViewById(R.id.txtDismissCity)
        imgDismissCity.setOnClickListener{
            bottomSheet.dismiss()
        }
         bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
         return bottomSheet
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnInputData = context as? OnInputData0
    }
}