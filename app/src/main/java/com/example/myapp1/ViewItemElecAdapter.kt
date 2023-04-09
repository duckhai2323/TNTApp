package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemImageText
import com.example.myapp1.home.adapter.ViewItemAdapter0

class ViewItemElecAdapter (private val listItemGoiY0:MutableList<ItemImageText>, val itemTitle: ClickInterface) : RecyclerView.Adapter<ViewItemElecAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemgoiy0,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgItem1 = findViewById<ImageView>(R.id.imgItem0)
            val txtTopic = findViewById<TextView>(R.id.txtTopic)
            imgItem1.setImageResource(listItemGoiY0[position].image)
            txtTopic.setText(listItemGoiY0[position].topic)

            holder.itemView.setOnClickListener{
                itemTitle.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItemGoiY0.size
    }
}