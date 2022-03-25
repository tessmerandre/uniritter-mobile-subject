package com.tessmerandre.activitiesandintents.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tessmerandre.activitiesandintents.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        val btNext = findViewById<Button>(R.id.btNext)
        btNext.setOnClickListener {
            onNextClick()
        }
    }

    private fun onNextClick() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

}