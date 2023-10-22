package com.jyotish.template.ui_screens.main_screen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jyotish.template.network.ResponseState
import com.jyotish.template.network.model.TestResponse
import com.jyotish.template.network.repository.DemoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DemoViewModel : ViewModel() {

    private val repository by lazy { DemoRepository.getInstance() }

    private val _testResponse = MutableLiveData<ResponseState<TestResponse>>()
    val testResponse: LiveData<ResponseState<TestResponse>> = _testResponse

    private val _uiState = MutableStateFlow<ResponseState<Unit>>(ResponseState.Loading)
    val uiState: StateFlow<ResponseState<Unit>> get() = _uiState

    fun getTestApi(type: String) = viewModelScope.launch {
        try {
            _testResponse.value = ResponseState.Loading
            val response = repository.getDemoApi(type = type)
           // _testResponse.value = ResponseState.Success(response)

        } catch (e: Exception) {
            e.printStackTrace()
            _testResponse.value = ResponseState.Error(e.message)
        } finally {
            // do something
        }
    }

    fun getDemoApi(type: String) = liveData {
        try {
            emit(ResponseState.Loading)
            val response = repository.getDemoApi(type = type)
            emit(ResponseState.Success(response))
        } catch (e: Exception){
            e.printStackTrace()
            emit(ResponseState.Error(e.message))
        }
    }

}