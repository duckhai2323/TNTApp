package com.example.myapp1

import android.os.Build
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import java.util.Date

class TimeCount(private var timestamp:com.google.firebase.Timestamp) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun timeCount():String{
        var date:Date = timestamp.toDate()
        val instant: Instant = date.toInstant()
        val now:Instant = Instant.now()
        val diff:Duration = Duration.between(instant,now)
        if (diff.toMinutes() < 1) {
            return "vừa xong"
        } else if (diff.toHours() < 1) {
            return diff.toMinutes().toString() + " phút trước"
        } else if (diff.toDays() < 1) {
            return diff.toHours().toString() + " giờ trước"
        } else if (diff.toDays() < 30) {
            return diff.toDays().toString() + " ngày trước"
        } else {
            return (diff.toDays() / 30).toString() + " tháng trước"
        }
    }
}