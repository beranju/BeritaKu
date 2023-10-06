package com.nextgen.beritaku.core.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    fun dateFormat(date: String): String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val dateTime = simpleDateFormat.parse(date)
        val now = Calendar.getInstance().timeInMillis
        val result = android.text.format.DateUtils.getRelativeTimeSpanString(dateTime.time, now, android.text.format.DateUtils.MINUTE_IN_MILLIS)
        return result.toString()
    }

    fun dateToTimeAgo(date: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val parsedTime: Date = formatter.parse(date)
        val currentDate: Date = Date()
        val timeDifferenceMillis: Long = currentDate.time - parsedTime.time
        // ** calculate
        val seconds = timeDifferenceMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return when{
            days > 0 -> "$days hari lalu"
            hours > 0 -> "$hours jam lalu"
            minutes > 0 -> "$minutes menit lalu"
            else -> "baru saja"
        }
    }

    fun getCurrentDayDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}