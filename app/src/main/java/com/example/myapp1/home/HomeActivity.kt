package com.example.myapp1.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapp1.*
import com.example.myapp1.electronic.DialogPhone
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private val homeFragment = HomeFragment()
private val profileFragment = ProfleFragment()
private val electronicFragment = ElectronicFragment()
class HomeActivity : AppCompatActivity(), OnInputData0 {

    private var dialogDSP: DialogPhone = DialogPhone()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val i = intent
        val email = i.getStringExtra("Email")
        val bundel = Bundle()
        bundel.putString("Email",email)
        profileFragment.arguments = bundel

        val navigation = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        navigation.background = null
        replaceFragment(homeFragment)
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
               R.id.home->replaceFragment(homeFragment)
                R.id.profile->replaceFragment(profileFragment)
                else ->replaceFragment(homeFragment)
            }
            true
        }

        val btnFloating = findViewById<FloatingActionButton>(R.id.btnFloating)
        btnFloating.setOnClickListener{
            var bottomSheetDialogDSP:BottomSheetDialogDSP = BottomSheetDialogDSP(dialogDSP)
            bottomSheetDialogDSP.show(supportFragmentManager,bottomSheetDialogDSP.tag)
        }
    }

    override fun sendData(str: String,obj:String) {
        if(obj == "city") {
            electronicFragment.update(str, obj)
        } else {
            dialogDSP.updateDate(str,obj)
        }
    }

    private fun replaceFragment(fragment:Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

    fun getFragment(str:String) {
        when(str){
            "electron" -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, electronicFragment)
                transaction.addToBackStack(TagElectron)
                transaction.commit()
            }
        }
    }

}

