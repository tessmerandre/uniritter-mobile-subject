package com.tessmerandre.listsndetails.data.model

import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.ui.detail.DetailArgument

data class Album(
    val userId: Int,
    val id: Int,
    val title: String,
) : ListItem {

    override fun getItemTitle() = title

    override fun getDetailArgument(): DetailArgument {
        return DetailArgument(title)
    }

}