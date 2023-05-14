package com.example.myapp1

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.ClickInterface
import com.example.myapp1.home.HomeActivity
import com.example.myapp1.home.HomeFragment
import com.example.myapp1.home.username
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Locale

class ChatFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<ItemUserChat>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var backButton: ImageView
    private var data: Boolean? = false
    private val mDbRef = FirebaseFirestore.getInstance()
    private var senderRoom: String? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater.inflate(R.layout.fragment_chat, container, false)
        val email = arguments?.getString("Email")
        mAuth = FirebaseAuth.getInstance()
        userList = ArrayList()
        backButton = view.findViewById(R.id.backFromChat)
        userRecyclerView = view.findViewById(R.id.userRecyclerView)
//        mDbRef.collection("users").get()
//            .addOnSuccessListener { result ->
//                userList.clear()
//                for (document in result) {
//                    val mUserName = document.data?.get("username").toString()
//                    val mEmail = document.data?.get("email").toString()
//                    senderRoom = mEmail + email
//                    userList.add(ItemUserChat("", "", mUserName,"","", mEmail))
//                }
//                adapter = context?.let { UserAdapter(it, userList, email) }!!
//                userRecyclerView.adapter = adapter
//                userRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//            }
//            .addOnFailureListener {
//                Toast.makeText(context,"Chưa có người nhắn tin", Toast.LENGTH_LONG)
//            }
        mDbRef.collection("chats")
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .addSnapshotListener { result, e ->
                userList.clear()
                for (document in result!!) {
                    val message = document.data?.get("message").toString()
                    val senderName = document.data?.get("senderName").toString()
                    val receiverName = document.data?.get("receiverName").toString()
                    val timestamp = document.getTimestamp("timeStamp")
                    val date = timestamp?.toDate()
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateStr = /*dateFormat.format(date)*/ ""
                    if (senderName == username || receiverName == username) {
                        var flag = false
                        for (i: ItemUserChat in userList) {
                            if (senderName == username && i.userName == receiverName) {
                                flag = true
                            }
                            if (receiverName == username && i.userName == senderName) {
                                flag = true
                            }
                        }
                        if (!flag) {
                            if (senderName == username){
                                userList.add(ItemUserChat( "", receiverName, dateStr, message, ""))
                            }
                            else {
                                userList.add(ItemUserChat( "", senderName, dateStr, message, ""))
                            }
                        }
                    }
                }
                /*for (i in 0 until userList.size) {
                    var user = userList[i]
                    val name = user.userName
                    mDbRef.collection("users").document(name)
                        .get()
                        .addOnSuccessListener { documentSnapshot ->
                            val imageProfile = documentSnapshot.data?.get("imageProfile").toString()
                            user.imageProfile = imageProfile
                            adapter = context?.let { UserAdapter(it, userList, email) }!!
                            userRecyclerView.adapter = adapter
                            userRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        }
                }*/
                mDbRef.collection("users")
                    .get()
                    .addOnSuccessListener {
                        if(!it.isEmpty) {
                            for(document in it.documents) {
                                val userName = document.data?.get("username").toString()
                                for(i in userList) {
                                    if(i.userName == userName) {
                                        i.imageProfile = document.data?.get("imageProfile").toString()
                                    }
                                }
                            }
                            adapter = UserAdapter(userList,object : ClickInterface{
                                override fun setOnClick(pos: Int) {
                                    val intent = Intent(context, ChatActivity::class.java)
                                    intent.putExtra("imageProfile",userList[pos].imageProfile)
                                    intent.putExtra("name", userList[pos].userName)
                                    startActivity(intent)
                                }
                            })
                            userRecyclerView.adapter = adapter
                            userRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        }
                    }
            }

        backButton.setOnClickListener {
            val fragment = HomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frameLayout, fragment)
            transaction!!.commit()
        }


        return view
    }

}