package com.example.myapp1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ViewItemPayAdapter(val context:Context,val listProduct:MutableList<ItemViewPay>):RecyclerView.Adapter<ViewItemPayAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.layout_itempay,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var client = findViewById<TextView>(R.id.txtClient1)
            var imageTin = findViewById<ImageView>(R.id.imgTin)
            var infor = findViewById<TextView>(R.id.txtInfoTin)
            var price = findViewById<TextView>(R.id.txtPriceTin)
            var status = findViewById<TextView>(R.id.txtStatusTin)
            var mess = findViewById<EditText>(R.id.txtStatus)

            client.text = listProduct[position].client
            Picasso.get().load(listProduct[position].imageProduct).into(imageTin)
            infor.text = listProduct[position].txtInfor
            price.text = listProduct[position].txtPrice1
            status.text = listProduct[position].txtStatus1
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}