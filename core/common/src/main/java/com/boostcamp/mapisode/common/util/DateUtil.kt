package com.boostcamp.mapisode.common.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedString(): String {
	val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
	return dateFormat.format(this)
}
