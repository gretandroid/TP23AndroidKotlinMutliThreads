package com.example.kotlin.mutlithreads

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.mutlithreads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onClickButtonEvent(view: View) {
        display("code du thread UI")
        display("code du thread UI")
        display("code du thread UI")
    }

    fun display(msg: String) {
        binding.threadOutputView.append("${msg}\n")
    }
}