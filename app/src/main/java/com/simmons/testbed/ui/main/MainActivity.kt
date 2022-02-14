package com.simmons.testbed.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.simmons.testbed.R
import com.simmons.testbed.databinding.ActivityMainBinding
import com.simmons.testbed.ui.check.CheckActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setObserver()
        setEditTextListener()
        setOnClickListener()
    }

    private fun setObserver() {
        viewModel.xBoundary.observe(this, {
            viewModel.setIsDataIsNotNull()
        })
        viewModel.yBoundary.observe(this, {
            viewModel.setIsDataIsNotNull()
        })
        viewModel.isDataIsNotNull.observe(this, {
            setButtonActive(it)
        })
        viewModel.isValidToSetBound.observe(this, {
            if (it){
                moveToCheckActivity()
            } else {
                Toast.makeText(this, "유효한 경계값을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setButtonActive(isActive: Boolean) {
        binding.btnSetBound.isEnabled = isActive
    }

    private fun moveToCheckActivity() {
        startActivity(Intent(baseContext, CheckActivity::class.java))
    }

    private fun setEditTextListener() {
        binding.etBoundX.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null){
                    viewModel.setXBoundary(null)
                } else {
                    viewModel.setXBoundary(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etBoundY.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null){
                    viewModel.setYBoundary(null)
                } else {
                    viewModel.setYBoundary(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setOnClickListener() {
        binding.btnSetBound.setOnClickListener {
            viewModel.setBound()
        }
    }
}
