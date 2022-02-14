package com.simmons.testbed.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simmons.testbed.model.SimmonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SimmonsRepository) : ViewModel() {

    private val _xBoundary = MutableLiveData<String?>()
    val xBoundary: LiveData<String?> = _xBoundary

    private val _yBoundary = MutableLiveData<String?>()
    val yBoundary: LiveData<String?> = _yBoundary

    private val _howMany = MutableLiveData<String?>()
    val howMany: LiveData<String?> = _howMany

    private val _isDataIsNotNull = MutableLiveData(false)
    val isDataIsNotNull: LiveData<Boolean> = _isDataIsNotNull

    private val _nowCheck = MutableLiveData("1")
    val nowCheck: LiveData<String> = _nowCheck

    private val _isValidToSetBound = MutableLiveData<Boolean>()
    val isValidToSetBound: LiveData<Boolean> = _isValidToSetBound

    private val _isValidToSetStoreNum = MutableLiveData<Boolean>()
    val isValidToSetStoreNum: LiveData<Boolean> = _isValidToSetStoreNum

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> = _isValid

    private val _checkStatus = MutableLiveData<Int>()
    val checkStatus: LiveData<Int> = _checkStatus

    fun setXBoundary(data: String?) {
        _xBoundary.value = data
    }

    fun setYBoundary(data: String?) {
        _yBoundary.value = data
    }

    fun setHowMany(data: String?) {
        _howMany.value = data
    }

    fun setIsDataIsNotNull() {
        _isDataIsNotNull.value = _xBoundary.value != null
                && _yBoundary.value != null
                && _howMany.value != null
    }

    fun setData() {
        val x = _xBoundary.value ?: return
        val y = _yBoundary.value ?: return
        val check = _nowCheck.value ?: return
        val count = _howMany.value ?: return

        viewModelScope.launch {
            _isValidToSetBound.value = repository.setBound(x, y) == 200
            _isValidToSetStoreNum.value = repository.setStoreNum(check, count) == 200
        }
    }

    fun stopCheck(){
        viewModelScope.launch {
            _howMany.value = "0"
            _nowCheck.value = "0"
            repository.setStoreNum(_howMany.value!!, _nowCheck.value!!)
        }
    }

    fun checkValid() {
        _isValid.value = _isValidToSetBound.value == true && _isValidToSetStoreNum.value == true
    }

    fun getCheckData(){
        viewModelScope.launch {
            _checkStatus.value = repository.getCheckStatus()
        }
    }
}
