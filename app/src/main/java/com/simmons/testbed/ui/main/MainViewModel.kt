package com.simmons.testbed.ui.main

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

    private val _isDataIsNotNull = MutableLiveData(false)
    val isDataIsNotNull: LiveData<Boolean> = _isDataIsNotNull

    private val _isValidToSetBound = MutableLiveData<Boolean>()
    val isValidToSetBound: LiveData<Boolean> = _isValidToSetBound

    fun setXBoundary(data: String?) {
        _xBoundary.value = data
    }

    fun setYBoundary(data: String?) {
        _yBoundary.value = data
    }

    fun setIsDataIsNotNull() {
        _isDataIsNotNull.value = _xBoundary.value != null && _yBoundary.value != null
    }

    fun setBound() {
        val x = _xBoundary.value ?: return
        val y = _yBoundary.value ?: return

        viewModelScope.launch {
            _isValidToSetBound.value = repository.setBound(x, y) == 200
        }
    }
}
