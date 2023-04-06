package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewItemAdapterTin(private val listTin:MutableList<Tin>) : RecyclerView.Adapter<ViewItemAdapterTin.ItemViewHolder>(){
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_tin,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var imageTin = findViewById<ImageView>(R.id.imgTin)
            var infor = findViewById<TextView>(R.id.txtInfoTin)
            var price = findViewById<TextView>(R.id.txtPriceTin)
            var status = findViewById<TextView>(R.id.txtStatusTin)

            imageTin.setImageResource(listTin[position].image)
            infor.text = listTin[position].info
            price.text = listTin[position].price
            status.text = listTin[position].status
        }
    }

    override fun getItemCount(): Int {
        return listTin.size
    }
}