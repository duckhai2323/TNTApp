package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewItemAdapterTin1(private var listTinComplete:MutableList<String>) : RecyclerView.Adapter<ViewItemAdapterTin1.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_banthanhcong,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var txtcontent = findViewById<TextView>(R.id.txtcontent)
            txtcontent.text = listTinComplete[position]
        }
    }

    override fun getItemCount(): Int {
        return listTinComplete.size
    }
}