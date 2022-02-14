package com.simmons.testbed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.simmons.testbed.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSetBound.setOnClickListener {
            startActivity(Intent(baseContext, CheckActivity::class.java))
        }
    }
}
