package com.example.myapp1.electronic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import com.example.myapp1.home.ClickInterface
import com.squareup.picasso.Picasso

class ViewItemBrandAdapter(private val listBrand:MutableList<ItemBrand>, var clickItem:ClickInterface):RecyclerView.Adapter<ViewItemBrandAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemhang,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var imageBrand = findViewById<ImageView>(R.id.imageBrand)
            var txtbrand = findViewById<TextView>(R.id.txtBrand)
            txtbrand.text = listBrand[position].name
            Picasso.get().load(listBrand[position].imageUrl).into(imageBrand)
            holder.itemView.setOnClickListener{
                clickItem.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listBrand.size
    }
}