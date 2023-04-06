package com.example.myapp1.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import com.example.myapp1.home.ItemGoiY

class ViewItemAdapter (private val listItemGoiY:MutableList<ItemGoiY>) : RecyclerView.Adapter<ViewItemAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemgoiyhomnay,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgItem1 = findViewById<ImageView>(R.id.imgItem1)
            val txtPrice = findViewById<TextView>(R.id.txtPrice)
            val txtStatus = findViewById<TextView>(R.id.txtStatus)
            imgItem1.setImageResource(listItemGoiY[position].Image)
            txtPrice.setText(listItemGoiY[position].price)
            txtStatus.setText(listItemGoiY[position].status)
        }
    }

    override fun getItemCount(): Int {
        return listItemGoiY.size
    }
}