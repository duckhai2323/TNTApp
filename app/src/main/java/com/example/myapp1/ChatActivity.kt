package com.example.myapp1

import android.annotation.SuppressLint
import android.content.*
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.username
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class ChatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var imageProfile:RoundedImageView
    private lateinit var sentUer:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val mImageProfile = intent.getStringExtra("imageProfile")
        val db = FirebaseFirestore.getInstance()

        messageRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)
        messageList = ArrayList()
        imageProfile = findViewById(R.id.imgProfile1)
        sentUer = findViewById(R.id.sentUser)
        Picasso.get().load(mImageProfile).into(imageProfile)
        sentUer.text = name.toString()

        db.collection("chats")
            .orderBy("timeStamp", Query.Direction.ASCENDING)
            .addSnapshotListener { result, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                messageList.clear()
                for (document in result!!) {
                    val message = document.data?.get("message").toString()
                    val senderName = document.data?.get("senderName").toString()
                    val receiverName = document.data?.get("receiverName").toString()
                    val date = document.data?.get("timeStamp")
                    if ((senderName == username && receiverName == name) || (receiverName == username && senderName == name)) {
                        messageList.add(Message(message, senderName, receiverName))
                    }
                }
                messageAdapter = MessageAdapter(this, messageList,mImageProfile.toString())
                messageAdapter.notifyDataSetChanged()
                messageRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                messageRecyclerView.adapter = messageAdapter
                messageRecyclerView.scrollToPosition(messageList.size - 1 )
            }
        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            if (message != "") {
                val chatRoomObject1 =
                    ChatRoom("100100", username, name, message, FieldValue.serverTimestamp())
                db.collection("chats").add(chatRoomObject1)
                messageBox.setText("")
            }
        }

//        val backButton: ImageView = findViewById(R.id.backFromChat)
//        backButton.setOnClickListener {
//            val intent = Intent(this@ChatActivity, ChatFragment::class.java)
//            startActivity(intent)
//        }
    }
}