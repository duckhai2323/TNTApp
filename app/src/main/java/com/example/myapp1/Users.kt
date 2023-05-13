package com.example.myapp1

import com.google.firebase.firestore.FieldValue

class Users(
    var username:String,
    var id:String,
    var fullName:String,
    var email:String,
    var numberPhone:String,
    var sex:String,
    var birth:String,
    var bio:String,
    var image:String,
    var timestamp: FieldValue,
    var dangban:MutableList<String>,
    var daban:MutableList<String>,
    var cart:MutableList<String>,
    var address:Map<String,String>
) {
}