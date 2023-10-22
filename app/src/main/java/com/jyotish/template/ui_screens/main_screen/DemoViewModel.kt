package com.jyotish.template.ui_screens.main_screen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jyotish.template.network.ResponseState
import com.jyotish.template.network.repository.DemoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Timer
import kotlin.concurrent.timerTask


class DemoViewModel : ViewModel() {

    private val repository by lazy { DemoRepository.getInstance() }
    private var oldPrice = 0
    private val _price = MutableLiveData<Int>()
    val price: LiveData<Int> = _price

    private val _percent = MutableLiveData<Double>()
     val percent: LiveData<Double> = _percent

    private val _uiState = MutableStateFlow<ResponseState<Unit>>(ResponseState.Loading)
    val uiState: StateFlow<ResponseState<Unit>> get() = _uiState

    /* fun getTestApi(type: String) = viewModelScope.launch {
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
 */
    fun getDemoApi(type: String) = liveData {
        try {
            emit(ResponseState.Loading)
            val response = repository.getDemoApi(type = type)
            emit(ResponseState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Error(e.message))
        }
    }


    fun updateData() {
        val funtimer: Timer = Timer()
        _price.value = 0
        _percent.value = 0.0
        funtimer.scheduleAtFixedRate(
            timerTask()
            {
                updateValueAndPercent()
            }, 5000, 5000
        )
    }

    private fun updateValueAndPercent() {
        val newPrice = (0..300).shuffled().last()
        _price.value = newPrice
        _percent.value = updatePercent(newPrice)
        oldPrice = newPrice
    }

    private fun updatePercent(newPrice: Int): Double {
        return (oldPrice.toDouble() / newPrice) * 100
    }

}