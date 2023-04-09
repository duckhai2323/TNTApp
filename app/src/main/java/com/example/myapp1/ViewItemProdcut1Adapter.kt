package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
import com.example.myapp1.home.adapter.ViewProductAdapter

class ViewItemProdcut1Adapter (private val listItemproduct:MutableList<ItemProduct>, val onProductClick: ClickInterface):
    RecyclerView.Adapter<ViewItemProdcut1Adapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemproduct1,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val imgProduct = findViewById<ImageView>(R.id.imgProduct)
            val txtInfor = findViewById<TextView>(R.id.txtInfor)
            val txtPrice1 = findViewById<TextView>(R.id.txtprice1)
            val txtStatus1 = findViewById<TextView>(R.id.address)

            imgProduct.setImageResource(listItemproduct[position].imageProduct)
            txtInfor.text = listItemproduct[position].txtInfor
            txtPrice1.text = listItemproduct[position].txtPrice1
            txtStatus1.text = listItemproduct[position].txtStatus1

            holder.itemView.setOnClickListener{
                onProductClick.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItemproduct.size
    }
}