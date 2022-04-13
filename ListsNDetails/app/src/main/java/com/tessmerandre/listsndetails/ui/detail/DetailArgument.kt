package com.tessmerandre.listsndetails.ui.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailArgument(
    val title: String,
    val overline: String? = null,
    val description: String? = null,
    val photoUrl: String? = null
) : Parcelable