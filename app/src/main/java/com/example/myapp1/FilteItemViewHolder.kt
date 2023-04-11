package com.example.myapp1

import android.media.Image
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilteItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    var txtTextItem:TextView = itemView.findViewById(R.id.txtTextItem)
    var imgItem:ImageView = itemView.findViewById(R.id.imgItem)
    var layoutItem:LinearLayout = itemView.findViewById(R.id.layoutItem)

    fun bindData (item:FilteItem) {
        txtTextItem.text = item.TextItem
        imgItem.setImageResource(item.imgItem)
        layoutItem.setBackgroundResource(item.layoutItem)
    }
}