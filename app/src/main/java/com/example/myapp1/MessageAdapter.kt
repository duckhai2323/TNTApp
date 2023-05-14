package com.example.myapp1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapp1.home.username
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>,val imgUrl:String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val item_received = 1
    val item_sent = 2

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceivedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receivedMessage = itemView.findViewById<TextView>(R.id.txt_received_message)
        val imgUser = itemView.findViewById<RoundedImageView>(R.id.imgSentUser)

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (username == currentMessage.senderName) {
            return item_sent
            print(item_sent)
        } else {
            return item_received
            print(item_received)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as ReceivedViewHolder
            holder.receivedMessage.text = currentMessage.message
            Picasso.get().load(imgUrl).into(holder.imgUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.layout_received, parent, false)
            return ReceivedViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.layout_sent, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}