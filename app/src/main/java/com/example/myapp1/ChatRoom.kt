package com.example.myapp1

import com.google.firebase.firestore.FieldValue

class ChatRoom(
    var roomId: String,
    var senderName:String?,
    var receiverName:String?,
    var message: String,
    var timeStamp: FieldValue
) {
}