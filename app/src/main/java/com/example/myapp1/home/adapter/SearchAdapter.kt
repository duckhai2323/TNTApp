package com.example.myapp1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ItemProduct

class SearchAdapter(val context: Context, val productList: ArrayList<ItemProduct>):
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("productList", productList)
            context.startActivity(intent)
        }
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textname = itemView.findViewById<TextView>(R.id.txt_name)
    }


}