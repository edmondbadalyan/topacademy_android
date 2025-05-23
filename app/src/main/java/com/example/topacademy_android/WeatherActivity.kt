package com.example.topacademy_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class WeatherActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        
        setupToolbar()
    }
    
    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
} 