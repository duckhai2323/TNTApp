package com.example.myapp1.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R

class ViewPageAdapter  (private val listItem:MutableList<Int>) : RecyclerView.Adapter<ViewPageAdapter.ItemViewHolder>(){
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemviewpage2,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgItem = findViewById<ImageView>(R.id.imgItem)
            var titleText = findViewById<TextView>(R.id.titleText)
            when(position) {
                0->{titleText.text = "Click Now"}
                1 ->{titleText.text = "Đặt đơn ngay!"}
                2 ->{titleText.text = "Khám phá ngay!"}
                else ->{titleText.text = "Click Now"}
            }
            imgItem.setImageResource(listItem[position])
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}