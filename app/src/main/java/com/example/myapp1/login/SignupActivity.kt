package com.example.myapp1.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapp1.R
import com.example.myapp1.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var auth:FirebaseAuth
        auth = Firebase.auth

        val db = Firebase.firestore

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var ds:MutableList<EditText> = mutableListOf()
        val edtFirst = findViewById<EditText>(R.id.edtFirst)
        ds.add(findViewById<EditText>(R.id.edtFirst))
        val edtLast = findViewById<EditText>(R.id.edtLast)
        ds.add(findViewById<EditText>(R.id.edtLast))
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        edtEmail.setText(edtEmail.text.toString()+"@gmail.com")
        ds.add(findViewById<EditText>(R.id.edtEmail))
        val userName = findViewById<EditText>(R.id.edtUser)
        ds.add(findViewById<EditText>(R.id.edtUser))
        val edtPassword1 = findViewById<EditText>(R.id.edtPassword1)
        ds.add(findViewById<EditText>(R.id.edtPassword1))
        val edtPassword2 = findViewById<EditText>(R.id.edtPassword2)
        ds.add(findViewById<EditText>(R.id.edtPassword2))
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        ds.add(findViewById<EditText>(R.id.edtPhone))
        val txtSig = findViewById<TextView>(R.id.txtSig)
        val txtCheckSign = findViewById<TextView>(R.id.txtCheckSign)

        txtSig.setOnClickListener {
            var j = 0
            var k = 0
            for(i in 0..(ds.size-1)) {
                if(ds[i].text.toString()=="") {
                    j++
                    k = i
                    break
                }
            }
            if(j!=0) {
                txtCheckSign.setText("Ban chua nhap day du thong tin")
                txtCheckSign.visibility = View.VISIBLE
                ds[k].requestFocus()
            } else{
                if(edtPassword1.text.toString().length < 8 ||edtPassword2.text.toString().length < 8 ) {
                    txtCheckSign.setText("Mat khau phai tren 8 ki tu a")
                    txtCheckSign.visibility= View.VISIBLE
                } else if(edtPassword1.text.toString() != edtPassword2.text.toString()) {
                    txtCheckSign.setText("Nhap sai mat khau r a")
                    txtCheckSign.visibility = View.VISIBLE
                    edtPassword1.setText("")
                    edtPassword2.setText("")
                    edtPassword1.requestFocus()
                } else {
                    if(edtPhone.text.toString().length != 10) {
                        txtCheckSign.setText("So dien thoai khong hop le")
                        txtCheckSign.visibility = View.VISIBLE
                        edtPhone.setText("")
                        edtPhone.requestFocus()
                    } else {
                        val inputEmail:String = edtEmail.text.toString()
                        val inputPassword:String = edtPassword1.text.toString()

                        auth.createUserWithEmailAndPassword(inputEmail,inputPassword)
                            .addOnCompleteListener(this){task->
                                if(task.isSuccessful){
                                    var dangban:MutableList<String> = mutableListOf()
                                    var daban:MutableList<String> = mutableListOf()
                                    var cart:MutableList<String> = mutableListOf()
                                    var address:Map<String,String> = hashMapOf(
                                        "addressInfor" to "",
                                        "name" to "",
                                        "numberPhoneX" to ""
                                    )
                                    var user = Users(userName.text.toString(),"100100",edtFirst.text.toString()+edtLast.text.toString(),inputEmail,edtPhone.text.toString(),"null","null","null","null", FieldValue.serverTimestamp(),dangban,daban,cart,address)
                                    db.collection("users").document(userName.text.toString()).set(user)
                                    Toast.makeText(this,"Tao tai khoan thanh cong",Toast.LENGTH_LONG)
                                    val intent_home = Intent(this,LoginActivity::class.java)
                                    startActivity(intent_home)
                                } else {

                                }
                            }
                            .addOnFailureListener{
                                txtCheckSign.setText("Tai khoan da ton tai")
                                txtCheckSign.visibility = View.VISIBLE
                            }
                    }
                }
            }
        }
    }
}