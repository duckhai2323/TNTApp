package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.adapter.ViewPageAdapter

class ViewPageElecAdapter (private val listItem:MutableList<Int>) : RecyclerView.Adapter<ViewPageElecAdapter.ItemViewHolder>(){
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemviewpage2_1,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgItem = findViewById<ImageView>(R.id.imgItem)
            imgItem.setImageResource(listItem[position])
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}