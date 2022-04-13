package com.tessmerandre.listsndetails.data.model

import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.ui.detail.DetailArgument

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
) : ListItem {

    override fun getItemTitle() = title
    override fun getItemDescription(): String {
        return if (completed) "Completo" else "Incompleto"
    }

    override fun getDetailArgument(): DetailArgument {
        return DetailArgument(title, getItemDescription())
    }

}