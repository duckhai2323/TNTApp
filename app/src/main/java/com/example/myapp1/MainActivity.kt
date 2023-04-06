package com.example.myapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapp1.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addEvent()
    }

    private fun addEvent() {
        val btnGetStart = findViewById<TextView>(R.id.btnGetStart)
        btnGetStart.setOnClickListener{
            val iLogin = Intent(this, LoginActivity::class.java)
            startActivity(iLogin)
        }
    }
}