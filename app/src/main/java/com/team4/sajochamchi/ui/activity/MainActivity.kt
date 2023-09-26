package com.team4.sajochamchi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team4.sajochamchi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        //nav view
        bottomNavigationView.itemIconTintList = null
    }
}