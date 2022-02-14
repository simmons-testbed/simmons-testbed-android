package com.simmons.testbed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.simmons.testbed.databinding.ActivityCheckBinding

class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSetStoreNum.setOnClickListener {
            // api call
        }
    }
}
