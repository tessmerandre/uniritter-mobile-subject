package com.tessmerandre.listsndetails.data.model

import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.ui.detail.DetailArgument

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) : ListItem {

    override fun getItemTitle() = title
    override fun getItemDescription() = body
    override fun getPhotoUrl() = null

    override fun getDetailArgument(): DetailArgument {
        return DetailArgument(title)
    }

}