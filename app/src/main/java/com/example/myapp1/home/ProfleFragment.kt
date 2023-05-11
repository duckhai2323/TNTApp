package com.example.myapp1.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
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
import com.example.myapp1.R
import com.example.myapp1.UpdateprofileActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
class ProfleFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var img: Uri
    private val IMAGE_REQUEST_CODE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profle, container, false)
        val txtUpdate: LinearLayout = view.findViewById(R.id.txtCaiDatTK)
        var txtUserName: TextView = view.findViewById(R.id.txtUserName)
        var cardProfile:CardView = view.findViewById(R.id.cardUser)
        var lloDonMua:LinearLayout = view.findViewById(R.id.lloDonMua)
        imageView = view.findViewById(R.id.imgProfile)

        val db = Firebase.firestore
        val storage = Firebase.storage
        db.collection("users").document(username)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    txtUserName.text = username
                    val imgStr = it.data?.get("imageProfile").toString()
                    Picasso.get().load(imgStr).into(imageView)
                }
            }

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
        txtUpdate.setOnClickListener {
            val i = Intent(requireContext(), UpdateprofileActivity::class.java)
            startActivity(i)
        }

        lloDonMua.setOnClickListener{
            val i = Intent(context, BillActivity::class.java)
            startActivity(i)
        }
        return view
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
}