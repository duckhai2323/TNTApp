package com.example.myapp1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface

class ViewItemFilteAdapter2(private val listItem:MutableList<ItemFiltte>,val click:ClickInterface) : RecyclerView.Adapter<ViewItemFilteAdapter2.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemfillter2,parent,false)
        return ItemViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var txtText1 = findViewById<TextView>(R.id.txtText1)
            var txtText2 = findViewById<TextView>(R.id.txtText2)
            txtText1.text = listItem[position].txtItem1
            txtText2.text = listItem[position].txtItem2
            txtText2.setTextColor(listItem[position].textColor)

            holder.itemView.setOnClickListener{
                click.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}