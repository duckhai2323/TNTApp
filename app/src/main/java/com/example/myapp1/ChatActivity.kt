package com.example.myapp1

import android.content.*
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.username
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var chatRoom: ChatRoom

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverEmail = intent.getStringExtra("email")
        val senderEmail = intent.getStringExtra("currentEmail")
        val db = FirebaseFirestore.getInstance()

        senderRoom = name + " " + username
        receiverRoom = username + " " + name
        supportActionBar?.title = name

        messageRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList, senderEmail)

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
                messageAdapter.notifyDataSetChanged()
                messageRecyclerView.layoutManager = LinearLayoutManager(this)
                messageRecyclerView.adapter = messageAdapter
            }

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val chatRoomObject1 = ChatRoom("100100", username, name, message, FieldValue.serverTimestamp())
//            db.collection("chats").document(senderRoom!!).collection("messages")
//                .add(messageObject).addOnSuccessListener {
//                    db.collection("chats").document(receiverRoom!!).collection("messages")
//                        .add(messageObject)
//                }.addOnFailureListener {
//
//                }
            db.collection("chats").add(chatRoomObject1)
            messageBox.setText("")

        }
    }
}