package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.ItemProduct
import com.squareup.picasso.Picasso

class ViewItemAdapterTin(private val listTin:MutableList<ItemProduct>,val onClick:ClickInterface) : RecyclerView.Adapter<ViewItemAdapterTin.ItemViewHolder>(){
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_tin,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var imageTin = findViewById<ImageView>(R.id.imgTin)
            var infor = findViewById<TextView>(R.id.txtInfoTin)
            var price = findViewById<TextView>(R.id.txtPriceTin)
            var status = findViewById<TextView>(R.id.txtStatusTin)

            Picasso.get().load(listTin[position].imageProduct).into(imageTin)
            infor.text = listTin[position].txtInfor
            price.text = listTin[position].txtPrice1
            status.text = listTin[position].txtStatus1

            holder.itemView.setOnClickListener{
                onClick.setOnClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listTin.size
    }
}