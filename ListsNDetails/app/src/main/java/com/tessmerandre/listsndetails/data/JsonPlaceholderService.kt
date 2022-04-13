package com.tessmerandre.listsndetails.data

import com.tessmerandre.listsndetails.data.model.*
import retrofit2.http.GET

interface JsonPlaceholderService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("photos")
    suspend fun getPhotos(): List<Photo>

    @GET("todos")
    suspend fun getTodos(): List<Todo>

    @GET("users")
    suspend fun getUsers(): List<User>

}