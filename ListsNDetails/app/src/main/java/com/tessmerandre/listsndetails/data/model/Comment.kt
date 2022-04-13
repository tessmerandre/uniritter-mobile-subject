package com.tessmerandre.listsndetails.data.model

import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.ui.detail.DetailArgument

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) : ListItem {

    override fun getItemTitle() = name
    override fun getItemDescription() = body

    override fun getDetailArgument(): DetailArgument {
        return DetailArgument(name, email, body)
    }

}