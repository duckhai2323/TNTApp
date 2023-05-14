package com.example.myapp1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.google.firebase.auth.FirebaseAuth
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class UserAdapter(val userList: ArrayList<ItemUserChat>,val click:ClickInterface):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_chat, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.textname.text = userList[position].userName
        Picasso.get().load(userList[position].imageProfile).into(holder.imgProfile)
        holder.dateChat.text = userList[position].date
        holder.mess.text = userList[position].mess
        holder.itemView.setOnClickListener {
            click.setOnClick(position)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textname = itemView.findViewById<TextView>(R.id.txt_name)
        val imgProfile = itemView.findViewById<RoundedImageView>(R.id.imgProfile)
        val dateChat = itemView.findViewById<TextView>(R.id.dateChat)
        val mess = itemView.findViewById<TextView>(R.id.mess)
    }


}