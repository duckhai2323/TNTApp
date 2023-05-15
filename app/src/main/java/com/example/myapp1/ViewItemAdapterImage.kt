package com.example.myapp1

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView

class ViewItemAdapterImage(private val listUri:MutableList<Uri>):RecyclerView.Adapter<ViewItemAdapterImage.ItemViewHolder>() {
    inner class ItemViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView){
        val image: RoundedImageView = itemView.findViewById(R.id.imgItemPush)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemimagepush,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.image.setImageURI(listUri[position])
    }

    override fun getItemCount(): Int {
        return listUri.size
    }
}