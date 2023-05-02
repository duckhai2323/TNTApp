package com.example.myapp1.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import com.example.myapp1.home.ClickInterface
import com.squareup.picasso.Picasso

class ViewItemAdapter1(private val listImage:MutableList<String>, val click:ClickInterface):RecyclerView.Adapter<ViewItemAdapter1.ItemViewHolder>(){
    inner class ItemViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemgoiyhomnay1,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgItem1 = findViewById<ImageView>(R.id.imgItem0)
            Picasso.get().load(listImage[position]).into(imgItem1)

            holder.itemView.setOnClickListener{
                click.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listImage.size
    }
}