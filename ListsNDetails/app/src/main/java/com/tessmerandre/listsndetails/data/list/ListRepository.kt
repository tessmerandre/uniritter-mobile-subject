package com.tessmerandre.listsndetails.data.list

import com.tessmerandre.listsndetails.data.JsonPlaceholderApi
import com.tessmerandre.listsndetails.data.JsonPlaceholderService
import com.tessmerandre.listsndetails.data.model.*

class ListRepository {

    // this should be injected...
    private val service = JsonPlaceholderApi.create(JsonPlaceholderService::class.java)

    suspend fun getPosts(): List<Post> = service.getPosts()

    suspend fun getComments(): List<Comment> = service.getComments()

    suspend fun getAlbums(): List<Album> = service.getAlbums()

    suspend fun getPhotos(): List<Photo> = service.getPhotos()

    suspend fun getTodos(): List<Todo> = service.getTodos()

    suspend fun getUsers(): List<User> = service.getUsers()

}