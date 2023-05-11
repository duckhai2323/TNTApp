package com.example.myapp1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.detail.DetailActivity
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.username
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ViewItemCartAdapter(var listProduct:MutableList<ItemCart>,var context:Context,val onclick:ClickInterface):RecyclerView.Adapter<ViewItemCartAdapter.ItemViewHolder>() {
    private val db = Firebase.firestore
    var listID:MutableList<String> = mutableListOf()
    lateinit var updates:Map<String,Any>
    lateinit var client:String
    lateinit var id_:String
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
            var checkbox = findViewById<CheckBox>(R.id.checkbox)

            client.text = listProduct[position].client
            Picasso.get().load(listProduct[position].imageProduct).into(imageTin)
            infor.text = listProduct[position].txtInfor
            price.text = listProduct[position].txtPrice1
            status.text = listProduct[position].txtStatus1
            checkbox.isChecked = listProduct[position].check

            var imgMore = findViewById<ImageView>(R.id.more)
            imgMore.setOnClickListener{ it ->
                val wrapper = ContextThemeWrapper(context,R.style.MyPopupMenu)
                val popupMenus = PopupMenu(wrapper,it)
                popupMenus.inflate(R.menu.menu1)
                popupMenus.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.product->{
                            val intent1 = Intent(context, ProductActivity::class.java)
                            db.collection("products").document(listProduct[position].id)
                                .get()
                                .addOnSuccessListener { it1->
                                    if(it1.exists()) {
                                        var str = it1.data?.get("category").toString()
                                        val index = str.lastIndexOf("/")
                                        val category = str.substring(startIndex = 0, endIndex = index)
                                        val product = str.substring(startIndex = (index+1))
                                        val bundle = Bundle()
                                        bundle.putString("category",category)
                                        bundle.putString("city","Toàn quốc")
                                        bundle.putString("product",product)
                                        intent1.putExtras(bundle)
                                        context.startActivity(intent1)
                                    }
                                }
                            true
                        }

                        R.id.delete->{
                            /**set delete*/
                            id_ = listProduct[position].id
                            AlertDialog.Builder(context)
                                .setTitle("Delete")
                                .setIcon(R.drawable.baseline_warning_amber_24)
                                .setMessage("Are you sure delete this Information")
                                .setPositiveButton("Yes"){
                                        dialog,_->
                                    listProduct.removeAt(position)
                                    notifyDataSetChanged()
                                    onclick.setOnClick(position)
                                    updateData()
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

            var constrantProduct = findViewById<ConstraintLayout>(R.id.constrantProduct)
            constrantProduct.setOnClickListener{
                val i1 = Intent(context, DetailActivity::class.java)
                i1.putExtra("id",listProduct[position].id)
                context.startActivity(i1)
            }

            var linearUser = findViewById<LinearLayout>(R.id.linearUser)
            linearUser.setOnClickListener{
                val i = Intent(context, ClientActivity::class.java)
                i.putExtra("clientProfile",listProduct[position].client)
                context.startActivity(i)
            }

            checkbox.setOnClickListener{
                listProduct[position].check = !listProduct[position].check
                notifyDataSetChanged()
                onclick.setOnClick(position)
            }
        }
    }

    private fun updateData() {
        db.collection("users").document(username)
            .get()
            .addOnSuccessListener {
                if(it.exists()){
                    listID = it.data?.get("cart") as MutableList<String>
                    listID.remove(id_)
                    updates = hashMapOf("cart" to listID)
                    db.collection("users").document(username).update(updates)
                }
            }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}