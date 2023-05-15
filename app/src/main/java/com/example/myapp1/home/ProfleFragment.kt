package com.example.myapp1.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.myapp1.BillActivity
import com.example.myapp1.CartActivity
import com.example.myapp1.ProductManagerFragment
import com.example.myapp1.R
import com.example.myapp1.UpdateprofileActivity
import com.example.myapp1.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
class ProfleFragment : Fragment() {
    val db = Firebase.firestore
    private lateinit var imageView: ImageView
    val storage = Firebase.storage
    private lateinit var img: Uri
    private val IMAGE_REQUEST_CODE = 1000
    lateinit var mActivityHome:HomeActivity
    private lateinit var view:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profle, container, false)
        imageView = view.findViewById(R.id.imgProfile)
        var mAuth = FirebaseAuth.getInstance()
        var setting:ImageView = view.findViewById(R.id.setting)
        setting.setOnClickListener{
            val i = Intent(requireContext(), UpdateprofileActivity::class.java)
            startActivity(i)
        }

        var cart:ImageView = view.findViewById(R.id.cartProfile)
        cart.setOnClickListener{
            val i = Intent(requireContext(),CartActivity::class.java)
            startActivity(i)
        }

        var chat:ImageView = view.findViewById(R.id.chatProfile)
        chat.setOnClickListener{
            mActivityHome.replaceFragment(chatFragment)
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomnavigation)
            bottomNavigationView.selectedItemId = R.id.chat
        }
        var logOut:TextView = view.findViewById(R.id.txtDangXuat)
        logOut.setOnClickListener {
            mAuth.signOut()
            val i2 = Intent(requireContext(), LoginActivity::class.java)

            startActivity(i2)
        }
        addEvent()
        return view
    }

    private fun addEvent() {
        DisplayProfile()
        ImageProifileChange()
        UpdateProfile()
        Bill()
    }

    private fun DisplayProfile() {
        var txtUserName: TextView = view.findViewById(R.id.txtUserName)
        var dangBan1:TextView = view.findViewById(R.id.txtDangBan1)
        var daBan1:TextView = view.findViewById(R.id.txtDaBan1)
        db.collection("users").document(username)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    txtUserName.text = username
                    val imgStr = it.data?.get("imageProfile").toString()
                    Picasso.get().load(imgStr).into(imageView)
                    val list1 = it.data?.get("daban") as MutableList<String>
                    daBan1.text = "Đã bán ${list1.size}"
                    val list2 = it.data?.get("dangban") as MutableList<String>
                    dangBan1.text = "Đang bán ${list2.size}"
                }
            }
    }

    private fun ImageProifileChange() {
        var cardProfile:CardView = view.findViewById(R.id.cardUser)
        cardProfile.setOnClickListener{
            pickImageGallery()
        }

        var txtMember:TextView = view.findViewById(R.id.txtMember)
        txtMember.setOnClickListener{
            val reference = storage.reference.child("users").child(username)
            reference.putFile(img).addOnCompleteListener{
                if(it.isSuccessful){
                    reference.downloadUrl.addOnSuccessListener {task ->
                        val userRef = db.collection("users").document(username)
                        userRef.update("imageProfile",task.toString()).addOnCompleteListener{
                        }
                    }
                }
            }
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data!=null && data.data!=null){
            img = data?.data!!
            imageView.setImageURI(data?.data)
        }
    }

    private fun Bill() {
        var lloDonMua:LinearLayout = view.findViewById(R.id.lloDonMua)
        var lloDonBan:LinearLayout = view.findViewById(R.id.lloDonBan)
        lloDonMua.setOnClickListener{
            val i = Intent(context, BillActivity::class.java)
            startActivity(i)
        }

        lloDonBan.setOnClickListener{
            mActivityHome.replaceFragment(productManagerFragment)
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomnavigation)
            bottomNavigationView.selectedItemId = R.id.productManager
        }
    }

    private fun UpdateProfile() {
        val txtUpdate: LinearLayout = view.findViewById(R.id.txtCaiDatTK)
        txtUpdate.setOnClickListener {
            val i = Intent(requireContext(), UpdateprofileActivity::class.java)
            startActivity(i)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivityHome = requireActivity() as HomeActivity
    }
}