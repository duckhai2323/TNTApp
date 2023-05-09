package com.example.myapp1

import android.provider.ContactsContract.CommonDataKinds.Email

class Message (
    var message:String,
    var senderEmail:String?,
    var receiverEmail:String?
) {
}