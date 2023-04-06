package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface

class ViewItemAdapterString(private val listDanhMuc:MutableList<String>, val itemClick: ClickInterface):
    RecyclerView.Adapter<ViewItemAdapterString.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemdsp,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var txtDanhMuc = findViewById<TextView>(R.id.txtDanhMuc)
            txtDanhMuc.text = listDanhMuc[position]

            holder.itemView.setOnClickListener {
                itemClick.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listDanhMuc.size
    }
}