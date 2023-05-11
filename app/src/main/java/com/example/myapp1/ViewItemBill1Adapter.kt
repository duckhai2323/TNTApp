package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.squareup.picasso.Picasso

class ViewItemBill1Adapter(private val listOrder:MutableList<ItemBill1>, val color:Int,val click:ClickInterface):RecyclerView.Adapter<ViewItemBill1Adapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itembill1,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var client = findViewById<TextView>(R.id.txtClient1)
            var imageTin = findViewById<ImageView>(R.id.imgTin)
            var infor = findViewById<TextView>(R.id.txtInfoTin)
            var price = findViewById<TextView>(R.id.txtPriceTin)
            var address = findViewById<TextView>(R.id.txtAddressTin)
            var status = findViewById<TextView>(R.id.txtStatus)

            client.text = listOrder[position].client
            Picasso.get().load(listOrder[position].imageProduct).into(imageTin)
            infor.text = listOrder[position].txtInfor
            price.text = listOrder[position].txtPrice1
            address.text = listOrder[position].txtAddress
            status.text = listOrder[position].status
            status.setBackgroundResource(color)

            status.setOnClickListener {
                click.setOnClick(position)
                if(status.text.toString() == "Đã nhận được hàng"){
                    listOrder.removeAt(position)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }
}