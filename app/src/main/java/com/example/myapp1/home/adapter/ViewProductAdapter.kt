package com.example.myapp1.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
import com.squareup.picasso.Picasso

class ViewProductAdapter(private val listItemproduct:MutableList<ItemProduct>, val onProductClick: ClickInterface):
    RecyclerView.Adapter<ViewProductAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemproduct,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgProduct = findViewById<ImageView>(R.id.imgProduct)
            val txtInfor = findViewById<TextView>(R.id.txtInfor)
            val txtPrice1 = findViewById<TextView>(R.id.txtprice1)
            val txtStatus1 = findViewById<TextView>(R.id.txtStatus1)

            Picasso.get().load(listItemproduct[position].imageProduct).into(imgProduct)
            txtInfor.setText(listItemproduct[position].txtInfor)
            txtPrice1.setText(listItemproduct[position].txtPrice1)
            txtStatus1.setText(listItemproduct[position].txtStatus1)

            holder.itemView.setOnClickListener{
                onProductClick.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItemproduct.size
    }
}