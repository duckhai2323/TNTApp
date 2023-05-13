package com.example.myapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.myapp1.home.username
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var n:Int = 0
class UpdateaddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var listAddress:MutableList<Address> = mutableListOf()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateaddress)
        val db = Firebase.firestore
        val userRef = db.collection("users").document(username)
        userRef.collection("address")
            .addSnapshotListener{result,e->
                n = 0
                if(result!=null){
                    listAddress.clear()
                    for(document in result!!){
                        val name = document.data?.get("name").toString()
                        val addressInfo = document.data?.get("addressInfo").toString()
                        val numberPhone = document.data?.get("numberPhonex").toString()
                        listAddress.add(Address(name, numberPhone, addressInfo))
                        n++
                    }
                    var rvAddress = findViewById<RecyclerView>(R.id.rvAddress)
                    rvAddress.adapter = ViewItemAdapterAddress(listAddress)
                    rvAddress.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                }
            }
        addEvent()
    }

    private fun addEvent() {
        CreateAddress()
    }

    private fun CreateAddress() {
        val txtAdd = findViewById<ConstraintLayout>(R.id.txtAdd)
        txtAdd.setOnClickListener {
            val i = Intent(this,AddresscreateActivity::class.java)
            startActivity(i)
        }
    }
}