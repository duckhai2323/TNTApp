package com.example.myapp1

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogFilterProduct(val listFilter:MutableList<ItemFiltte>):BottomSheetDialogFragment() {
    lateinit var bottomSheet:BottomSheetDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheet =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        var view: View = LayoutInflater.from(context).inflate(R.layout.layout_fillter,null)

        var txtProduct:TextView = view.findViewById(R.id.txtProduct)
        txtProduct.text = product
        txtProduct.setTextColor(ContextCompat.getColor(requireContext(),R.color.textColor))



        var rvFilter:RecyclerView = view.findViewById(R.id.rvFillter)
        rvFilter.adapter = ViewItemFilteAdapter2(listFilter,object:ClickInterface{
            override fun setOnClick(pos: Int) {

            }
        })
        rvFilter.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        var grRadius:RadioGroup = view.findViewById(R.id.grRadius)
        grRadius.setOnCheckedChangeListener{ group,checkId->
            if(checkId == R.id.radioButton1) {

            }

            if(checkId == R.id.radioButton2) {

            }
        }

        bottomSheet.setContentView(view)
        bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.setCancelable(false)
        return bottomSheet
    }
}