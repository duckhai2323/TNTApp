package com.example.myapp1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.ItemProduct
import com.squareup.picasso.Picasso

class ViewItemCartAdapter(var listProduct:MutableList<ItemCart>,var context:Context):RecyclerView.Adapter<ViewItemCartAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.layout_itemcart,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            var client = findViewById<TextView>(R.id.txtClient1)
            var imageTin = findViewById<ImageView>(R.id.imgTin)
            var infor = findViewById<TextView>(R.id.txtInfoTin)
            var price = findViewById<TextView>(R.id.txtPriceTin)
            var status = findViewById<TextView>(R.id.txtStatusTin)

            client.text = listProduct[position].client
            Picasso.get().load(listProduct[position].imageProduct).into(imageTin)
            infor.text = listProduct[position].txtInfor
            price.text = listProduct[position].txtPrice1
            status.text = listProduct[position].txtStatus1

            var imgMore = findViewById<ImageView>(R.id.more)
            imgMore.setOnClickListener{ it ->
                val wrapper = ContextThemeWrapper(context,R.style.MyPopupMenu)
                val popupMenus = PopupMenu(wrapper,it)
                popupMenus.inflate(R.menu.menu1)
                popupMenus.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.delete->{
                            /**set delete*/
                            AlertDialog.Builder(context)
                                .setTitle("Delete")
                                .setIcon(R.drawable.baseline_warning_amber_24)
                                .setMessage("Are you sure delete this Information")
                                .setPositiveButton("Yes"){
                                        dialog,_->
                                    listProduct.removeAt(position)
                                    notifyDataSetChanged()
                                    Toast.makeText(context,"Deleted this Information", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                                .setNegativeButton("No"){
                                        dialog,_->
                                    dialog.dismiss()
                                }
                                .create()
                                .show()

                            true
                        }
                        else-> true
                    }
                }
                popupMenus.show()
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenus)
                menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                    .invoke(menu,true)
            }

            holder.itemView.setOnClickListener{
                val i1 = Intent(context, DetailActivity::class.java)
                i1.putExtra("id",listProduct[position].id)
                context.startActivity(i1)
            }
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}