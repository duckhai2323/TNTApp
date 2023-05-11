package com.example.myapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.squareup.picasso.Picasso

class ViewItemBill2Adapter(private val listOrder:MutableList<ItemBill2>,val color:Int,val click:ClickInterface):RecyclerView.Adapter<ViewItemBill2Adapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itembill2,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var imageTin = findViewById<ImageView>(R.id.imgTin)
            var infor = findViewById<TextView>(R.id.txtInfoTin)
            var price = findViewById<TextView>(R.id.txtPriceTin)
            var address = findViewById<TextView>(R.id.txtAddressTin)
            var status = findViewById<TextView>(R.id.txtStatus)
            var receiverName = findViewById<TextView>(R.id.txtName)
            var receiverNumberPhone = findViewById<TextView>(R.id.txtNumberPhone)
            var receiverAddress = findViewById<TextView>(R.id.txtInfor)


            receiverName.text = listOrder[position].receiverName
            receiverNumberPhone.text = listOrder[position].receiverNumberPhone
            receiverAddress.text = listOrder[position].receiverAddress
            Picasso.get().load(listOrder[position].imageProduct).into(imageTin)
            infor.text = listOrder[position].txtInfor
            price.text = listOrder[position].txtPrice1
            address.text = listOrder[position].txtAddress
            status.text = listOrder[position].status
            status.setBackgroundResource(color)

            status.setOnClickListener{
                click.setOnClick(position)
                if(listOrder[position].status == "Xác nhận"){
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