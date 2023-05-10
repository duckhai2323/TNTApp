package com.example.myapp1

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

class Message (
    var message:String,
    var senderEmail:String?,
    var receiverEmail:String?,
    var timestamp: FieldValue
) {
}