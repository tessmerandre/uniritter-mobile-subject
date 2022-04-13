package com.tessmerandre.listsndetails.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tessmerandre.listsndetails.databinding.ActivityMainBinding
import com.tessmerandre.listsndetails.ui.list.ListActivity
import com.tessmerandre.listsndetails.ui.list.ListType

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        binding.btAlbums.setOnClickListener {
            goToList(ListType.ALBUMS)
        }

        binding.btComments.setOnClickListener {
            goToList(ListType.COMMENTS)
        }

        binding.btPhotos.setOnClickListener {
            goToList(ListType.PHOTOS)
        }

        binding.btPosts.setOnClickListener {
            goToList(ListType.POSTS)
        }

        binding.btTodos.setOnClickListener {
            goToList(ListType.TODOS)
        }

        binding.btUsers.setOnClickListener {
            goToList(ListType.USERS)
        }
    }

    private fun goToList(listType: ListType) {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra(ListActivity.ARG_LIST_TYPE, listType)
        startActivity(intent)
    }

}