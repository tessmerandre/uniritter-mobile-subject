package com.tessmerandre.listsndetails.data.model

import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.ui.detail.DetailArgument

data class Photo(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : ListItem {

    override fun getItemTitle() = title
    override fun getPhotoUrl() = thumbnailUrl

    override fun getDetailArgument(): DetailArgument {
        return DetailArgument(title = title, photoUrl = thumbnailUrl)
    }

}