package com.simmons.testbed.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.simmons.testbed.R
import com.simmons.testbed.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var isRun: Boolean = false
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private val handlerTask = object : Runnable {
        override fun run() {
            if (isRun) {
                viewModel.getCheckData()
                handler.postDelayed(this, 1000)
            }
        }
    }

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")

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
        viewModel.howMany.observe(this, {
            viewModel.setIsDataIsNotNull()
        })
        viewModel.cryType.observe(this, {
            viewModel.setIsDataIsNotNull()
        })
        viewModel.isDataIsNotNull.observe(this, {
            setButtonActive(it)
        })
        viewModel.isValidToSetBound.observe(this, {
            if (it) {
                viewModel.checkValid()
            } else {
                Toast.makeText(this, "유효한 경계값을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isValidToSetStoreNum.observe(this, {
            if (it) {
                viewModel.checkValid()
            } else {
                Toast.makeText(this, "이미 센서가 실행중이니 중지 후 데이터를 변경해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isValid.observe(this, {
            checkDataRepeated()
        })
        viewModel.checkStatus.observe(this, {
            binding.tvCheckResult.visibility = View.VISIBLE
            binding.tvCheckResultTime.visibility = View.VISIBLE
            binding.tvCheckResult.text = when (it) {
                3 -> "체크 인원수가 센서 범위내 인원수와 일치하지 않습니다."
                2 -> "아이가 이동반경 범위에서 벗어났습니다."
                1 -> "아이가 이동반경 오차범위(5) 이내에 위치합니다."
                0 -> "아이가 이동반경 범위 내에 있습니다."
                else -> return@observe
            }
            binding.tvCheckResultTime.text = LocalDateTime.now().format(formatter)
        })
        viewModel.isCrying.observe(this, {
            binding.tvCryResult.visibility = View.VISIBLE
            binding.tvCryResult.text = if (it) "아이가 울고 있습니다." else "아이가 울고 있지 않습니다."
        })
    }

    private fun setButtonActive(isActive: Boolean) {
        binding.btnSetBound.isEnabled = isActive
    }

    private fun setEditTextListener() {
        binding.etBoundX.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s.toString().isEmpty()) {
                    viewModel.setXBoundary(null)
                } else {
                    viewModel.setXBoundary(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etBoundY.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s.toString().isEmpty()) {
                    viewModel.setYBoundary(null)
                } else {
                    viewModel.setYBoundary(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etStoreNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s.toString().isEmpty()) {
                    viewModel.setHowMany(null)
                } else {
                    viewModel.setHowMany(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setOnClickListener() {
        binding.btnSetBound.setOnClickListener {
            if (isRun) {
                isRun = false
                viewModel.stopCheck()
                binding.btnSetBound.text = "아이 이동반경 검사하기"
                binding.tvCheckResult.visibility = View.INVISIBLE
                binding.tvCheckResultTime.visibility = View.INVISIBLE
                binding.tvCryResult.visibility = View.INVISIBLE
            } else {
                isRun = true
                viewModel.setData()
                binding.btnSetBound.text = "중지하기"
            }
        }
        binding.rgCrySelect.setOnCheckedChangeListener { _, checkedId ->
            val cryType = when (checkedId) {
                binding.rbCry.id -> 0
                binding.rbNose.id -> 1
                binding.rbLaugh.id -> 2
                binding.rbSilence.id -> 3
                else -> return@setOnCheckedChangeListener
            }
            viewModel.setCryType(cryType)
            binding.ivCryPlay.visibility = View.VISIBLE
        }
        binding.ivCryPlay.setOnClickListener {
            playCryMedia()
        }
    }

    private fun playCryMedia() {
        viewModel.cryType.value?.let {
            val cryFile = when (it) {
                0 -> R.raw.realcry
                1 -> R.raw.nosesound
                2 -> R.raw.laugh
                3 -> R.raw.silence
                else -> return@let
            }
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer!!.stop()
            }
            mediaPlayer = MediaPlayer.create(baseContext, cryFile)
            mediaPlayer!!.start()
        }
    }

    private fun checkDataRepeated() {
        handler.post(handlerTask)
    }
}
