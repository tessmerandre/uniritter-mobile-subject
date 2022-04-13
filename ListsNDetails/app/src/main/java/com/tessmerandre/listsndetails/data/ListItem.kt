package com.tessmerandre.listsndetails.data

import com.tessmerandre.listsndetails.ui.detail.DetailArgument

interface ListItem {
    fun getItemTitle(): String
    fun getItemDescription(): String? = null
    fun getPhotoUrl(): String? = null

    fun getDetailArgument(): DetailArgument
}