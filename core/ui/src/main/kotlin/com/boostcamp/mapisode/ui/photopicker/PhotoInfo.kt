package com.boostcamp.mapisode.ui.photopicker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class PhotoInfo(
	val uri: String,
	val dateTaken: Date?,
) : Parcelable
