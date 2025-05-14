package com.example.topacademy_android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.topacademy_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    // Переменные для хранения времени вызова методов
    private var onCreateTime: Long = 0
    private var onStartTime: Long = 0
    private var onResumeTime: Long = 0
    private var onPauseTime: Long = 0
    private var onStopTime: Long = 0
    private var onRestartTime: Long = 0
    private var onDestroyTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onCreateTime = System.currentTimeMillis()
        Log.d(TAG, "onCreate: $onCreateTime мс")
    }

    override fun onStart() {
        super.onStart()
        onStartTime = System.currentTimeMillis()
        Log.d(TAG, "onStart: $onStartTime мс, разница с onCreate = ${onStartTime - onCreateTime} мс")
    }

    override fun onResume() {
        super.onResume()
        onResumeTime = System.currentTimeMillis()
        Log.d(TAG, "onResume: $onResumeTime мс, разница с onStart = ${onResumeTime - onStartTime} мс")
    }

    override fun onPause() {
        super.onPause()
        onPauseTime = System.currentTimeMillis()
        Log.d(TAG, "onPause: $onPauseTime мс, разница с onResume = ${onPauseTime - onResumeTime} мс")
    }

    override fun onStop() {
        super.onStop()
        onStopTime = System.currentTimeMillis()
        Log.d(TAG, "onStop: $onStopTime мс, разница с onPause = ${onStopTime - onPauseTime} мс")
    }

    override fun onRestart() {
        super.onRestart()
        onRestartTime = System.currentTimeMillis()
        Log.d(TAG, "onRestart: $onRestartTime мс, разница с onStop = ${onRestartTime - onStopTime} мс")
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyTime = System.currentTimeMillis()
        Log.d(TAG, "onDestroy: $onDestroyTime мс, разница с onStop = ${onDestroyTime - onStopTime} мс")
    }
}