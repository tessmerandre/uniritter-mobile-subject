package com.tessmerandre.activitiesandintents.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tessmerandre.activitiesandintents.R

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setupViews()
    }

    private fun setupViews() {
        val btBack = findViewById<Button>(R.id.btBack)
        btBack.setOnClickListener {
            onBackClick()
        }

        val btNext = findViewById<Button>(R.id.btNext)
        btNext.setOnClickListener {
            onNextClick()
        }
    }

    private fun onBackClick() {
        finish()
    }

    private fun onNextClick() {
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }

}