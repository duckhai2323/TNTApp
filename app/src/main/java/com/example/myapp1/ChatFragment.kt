package com.example.myapp1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<String>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private val mDbRef = FirebaseFirestore.getInstance()
    lateinit var mActivityHome: HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        mAuth = FirebaseAuth.getInstance()
        userList = ArrayList()
        adapter = UserAdapter(requireContext(), userList)

        userRecyclerView = view.findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userRecyclerView.adapter = adapter

        mDbRef.collection("users").get()
            .addOnSuccessListener { result ->
                userList.clear()
                for (document in result) {
                    val user = document.data?.get("fullName").toString()
                    userList.add(user)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Chưa có người nhắn tin", Toast.LENGTH_LONG).show()
            }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivityHome = requireActivity() as HomeActivity
    }
}