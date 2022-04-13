package com.tessmerandre.listsndetails.data.model

import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.ui.detail.DetailArgument

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: UserAddress,
    val phone: String,
    val website: String,
    val company: UserCompany
) : ListItem {

    override fun getItemTitle() = name
    override fun getItemDescription() = email

    override fun getDetailArgument(): DetailArgument {
        return DetailArgument(title = name, overline = username, description = makeDetailDescription())
    }

    private fun makeDetailDescription(): String {
        return """
            Email: $email
            Phone: $phone
            Website: $website
            Address: ${address.completed}
            Company: ${company.name}
        """.trimIndent()
    }

}

data class UserAddress(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

data class UserCompany(
    val name: String,
    val catchPhrase: String,
    val bs: String
)