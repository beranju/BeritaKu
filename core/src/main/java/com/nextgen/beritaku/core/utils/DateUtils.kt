package com.nextgen.beritaku.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun dateFormat(date: String): String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val dateTime = simpleDateFormat.parse(date)
        val now = Calendar.getInstance().timeInMillis
        val result = android.text.format.DateUtils.getRelativeTimeSpanString(dateTime.time, now, android.text.format.DateUtils.MINUTE_IN_MILLIS)
        return result.toString()
    }
}