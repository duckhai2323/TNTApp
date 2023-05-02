package com.example.myapp1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface

class ViewItemFilteAdapter1(private val itemList:MutableList<FilteItem>, val onClik:ClickInterface):RecyclerView.Adapter<FilteItemViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteItemViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.layout_itemfillter1,parent,false)
        return FilteItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilteItemViewHolder, position: Int) {
        val item:FilteItem = itemList[position]
        holder.bindData(item)
        holder.itemView.setOnClickListener{
            onClik.setOnClick(position)

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(data:FilteItem,pos:Int){
        itemList[pos] = data
        notifyItemChanged(pos)
    }
}