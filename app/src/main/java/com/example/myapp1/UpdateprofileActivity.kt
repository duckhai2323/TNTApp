package com.example.myapp1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapp1.home.username
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
class UpdateprofileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateprofile)

        var txtname = findViewById<TextView>(R.id.txtname)
        var txtUser = findViewById<TextView>(R.id.txtUser)
        var txtSex = findViewById<TextView>(R.id.txtSex)
        var txtNumberPhone = findViewById<TextView>(R.id.txtNumberPhone)
        var txtEmail = findViewById<TextView>(R.id.txtEmail)
        val db = Firebase.firestore
        db.collection("users").whereEqualTo("username",username)
            .addSnapshotListener {result,e->
                if(result!=null) {
                    for(document in result!!){
                        val fullName = document.data?.get("fullName")?.toString()
                        val userName = document.data?.get("username")?.toString()
                        var numberPhone = document.data?.get("numberPhone")?.toString()
                        numberPhone = "********" + numberPhone?.substring(8)
                        txtname.text = fullName
                        txtUser.text = userName
                        txtEmail.text = document.data?.get("email").toString()
                        txtNumberPhone.text = numberPhone
                    }
                }
            }
        addEventUpdate()
    }

    private fun addEventUpdate() {
        updateAdress()
    }

    private fun updateAdress() {
        val updateAdd = findViewById<LinearLayout>(R.id.updateDiaChi)
        updateAdd.setOnClickListener{
            val i = Intent(this,UpdateaddressActivity::class.java)
            startActivity(i)
        }
    }
}