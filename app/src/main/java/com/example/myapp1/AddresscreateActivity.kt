package com.example.myapp1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapp1.home.username
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddresscreateActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresscreate)

        var txtHovaTen = findViewById<EditText>(R.id.edtHovaTen)
        var txtSodienThoai = findViewById<EditText>(R.id.edtSodienThoai)
        var txtDiaChi = findViewById<EditText>(R.id.edtDiaChi)
        var txtSonha = findViewById<EditText>(R.id.edtSonha)
        var txtHoanThanh = findViewById<TextView>(R.id.btnHoanThanh)
        var txtNhaRieng = findViewById<TextView>(R.id.txtNhaRieng)
        var txtVanPhong = findViewById<TextView>(R.id.txtVanPhong)
        var database: DatabaseReference = FirebaseDatabase.getInstance().reference
        txtHoanThanh.setOnClickListener{
            if(txtHovaTen.text.toString().isEmpty() || txtDiaChi.text.toString().isEmpty()||txtSonha.text.toString().isEmpty() || txtSodienThoai.text.toString().isEmpty()){
                Toast.makeText(this,"Nhap day du thong tin",Toast.LENGTH_LONG).show()
            } else if(txtSodienThoai.text.toString().length != 10) {
                Toast.makeText(this,"So dien thoai khong hop le", Toast.LENGTH_LONG).show()
            } else {
                var addressInfor = txtSonha.text.toString()
                var name = txtHovaTen.text.toString()
                var numberPhoneX = "(+84)" + txtSodienThoai.text.toString().substring(1)
                var address:Address = Address(name,numberPhoneX,addressInfor)
                val db = Firebase.firestore
                val userRef = db.collection("users").document(username)
                userRef.collection("address").document((n+1).toString()).set(address)
                txtHovaTen.setText("")
                txtSodienThoai.setText("")
                txtSonha.setText("")
                txtDiaChi.setText("")
                var map:Map<String,String> = hashMapOf(
                    "addressInfor" to addressInfor,
                    "name" to name,
                    "numberPhoneX" to numberPhoneX
                )
                var mapAddress:Map<String,Map<String,String>> = hashMapOf("address" to map)
                db.collection("users").document(username).update(mapAddress)
                onBackPressed()
            }
        }
    }
}