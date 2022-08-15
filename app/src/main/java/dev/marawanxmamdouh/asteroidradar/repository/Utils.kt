package dev.marawanxmamdouh.asteroidradar.repository

import android.icu.text.SimpleDateFormat
import java.util.*


private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

val currentDate: String
    get() = dateFormat.format(Calendar.getInstance().time).toString()

val endDate: String
    get() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        return dateFormat.format(calendar.time).toString()
    }