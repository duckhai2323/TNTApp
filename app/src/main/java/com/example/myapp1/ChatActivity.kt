package com.example.myapp1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatActivity : AppCompatActivity() {
//
//    private lateinit var userRecyclerView: RecyclerView
//    private lateinit var userList: ArrayList<Users>
//    private lateinit var adapter: UserAdapter
//    private lateinit var mAuth: FirebaseAuth
//    private val mDbRef = FirebaseFirestore.getInstance()
//
    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_chat)
//
//        mAuth = FirebaseAuth.getInstance()
//        userList = ArrayList()
//        adapter = UserAdapter(this, userList)
//
//        userRecyclerView = findViewById(R.id.userRecyclerView)
//        userRecyclerView.layoutManager = LinearLayoutManager(this)
//        userRecyclerView.adapter = adapter
//        mDbRef.collection("users").get()
//            .addOnSuccessListener { result ->
//                userList.clear()
//                for (document in result) {
//                    val user = document.toObject(Users::class.java)
//                    userList.add(user)
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(this@ChatActivity,"Chưa có người nhắn tin", Toast.LENGTH_LONG)
//            }
        }
}