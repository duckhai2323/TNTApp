package com.example.myapp1.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import com.squareup.picasso.Picasso

class ViewPageDetailAdapter(private val list:MutableList<String>) : RecyclerView.Adapter<ViewPageDetailAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_detail,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgDetail = findViewById<ImageView>(R.id.imgDetail)
            Picasso.get().load(list[position]).into(imgDetail)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}