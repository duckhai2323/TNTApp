package com.example.myapp1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<ItemUserChat>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private val mDbRef = FirebaseFirestore.getInstance()
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater.inflate(R.layout.fragment_chat, container, false)
        mAuth = FirebaseAuth.getInstance()
        userList = ArrayList()
        userRecyclerView = view.findViewById(R.id.userRecyclerView)
        mDbRef.collection("users").get()
            .addOnSuccessListener { result ->
                userList.clear()
                for (document in result) {
                        val mUserName = document.data?.get("username").toString()
                        userList.add(ItemUserChat("","",mUserName,"",""))
                }
                adapter = context?.let { UserAdapter(it, userList) }!!
                userRecyclerView.adapter = adapter
                userRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
            .addOnFailureListener {
                Toast.makeText(context,"Chưa có người nhắn tin", Toast.LENGTH_LONG)
            }
        return view
    }

}