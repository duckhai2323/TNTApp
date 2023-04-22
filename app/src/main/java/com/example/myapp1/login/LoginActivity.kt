package com.example.myapp1.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp1.R
import com.example.myapp1.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private var auth:FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        addEvent()
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        edtEmail.setText(edtEmail.text.toString()+"tranduckhai@gmail.com")
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        edtPassword.setText("12345678")
    }

    private fun addEvent() {
        SignUp()
        Login()
    }

    private fun Login() {
        val txtLogin = findViewById<TextView>(R.id.txtLogin)
        txtLogin.setOnClickListener {
            val edtEmail = findViewById<EditText>(R.id.edtEmail)
            val edtPassword = findViewById<EditText>(R.id.edtPassword)
            var inputEmail:String = edtEmail.text.toString()
            var inputPassword:String = edtPassword.text.toString()

            if(inputPassword.isEmpty()) {
                Toast.makeText(this@LoginActivity,"Nhap day du thong tin vao a",Toast.LENGTH_LONG)
            } else {

                auth.signInWithEmailAndPassword(inputEmail,inputPassword)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful) {
                            val user = auth.currentUser
                            val intent_home = Intent(this, HomeActivity::class.java)
                            intent_home.putExtra("Email",inputEmail)
                            startActivity(intent_home)
                        }else {
                            Toast.makeText(this@LoginActivity,"Mat khau hoac tai khoan khong dung",Toast.LENGTH_LONG)
                            edtEmail.setText("@gmail.com")
                            edtPassword.setText("")
                        }
                    }
                }
            }
        }

    private fun SignUp() {
        val txtSignUp = findViewById<TextView>(R.id.txtSignUp)
        txtSignUp.setOnClickListener{
            val edtEmail = findViewById<EditText>(R.id.edtEmail)
            val edtPassword = findViewById<EditText>(R.id.edtPassword)
            edtEmail.setText("@gmail.com")
            edtPassword.setText("")
            val intent2 = Intent(this, SignupActivity::class.java)
            startActivity(intent2)
        }
    }
}