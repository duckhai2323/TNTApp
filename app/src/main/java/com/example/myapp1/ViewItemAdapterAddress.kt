package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewItemAdapterAddress (private val listAddress: MutableList<Address>):RecyclerView.Adapter<ViewItemAdapterAddress.ItemViewHolder>(){
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_address,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var txtName = findViewById<TextView>(R.id.txtName1)
            var txtnumberPhonex = findViewById<TextView>(R.id.txtnumberPhone)
            var txtInfor = findViewById<TextView>(R.id.txtInfor1)

            txtName.text = listAddress[position].name
            txtnumberPhonex.text = listAddress[position].numberPhonex
            txtInfor.text = listAddress[position].addressInfo
        }
    }

    override fun getItemCount(): Int {
        return listAddress.size
    }
}