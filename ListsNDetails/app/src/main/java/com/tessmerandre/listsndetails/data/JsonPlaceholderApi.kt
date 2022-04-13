package com.tessmerandre.listsndetails.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object JsonPlaceholderApi {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit = makeRetrofitClient()

    private fun makeRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

}