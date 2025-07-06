package com.giraffe.caffeineapp.screen.cupsize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CupSizeViewModel : ViewModel(), CupSizeScreenInteraction {
    private val _state = MutableStateFlow(CoffeeSizeScreenState())
    val state = _state.asStateFlow()
    override fun selectSize(size: CupSize) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedSize = size) }
        }
    }

    override fun selectPercentage(percentage: CoffeePercentage) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedPercentage = percentage) }
        }
    }
}