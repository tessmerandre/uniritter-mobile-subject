package com.tessmerandre.activitiesandintents.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tessmerandre.activitiesandintents.R

class FourthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)
        setupViews()
    }

    private fun setupViews() {
        val btBack = findViewById<Button>(R.id.btBack)
        btBack.setOnClickListener {
            onBackClick()
        }
    }

    private fun onBackClick() {
        finish()
    }

}