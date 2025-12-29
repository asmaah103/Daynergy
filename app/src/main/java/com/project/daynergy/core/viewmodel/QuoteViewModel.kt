package com.project.daynergy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.daynergy.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class QuoteUiState(
    val text: String = "",
    val author: String = "",
    val isLoading: Boolean = false
)

class QuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuoteUiState(isLoading = true))
    val state: StateFlow<QuoteUiState> = _state

    init {
        loadQuote()
    }

    fun loadQuote() {
        viewModelScope.launch {
            _state.value = QuoteUiState(isLoading = true)

            try {
                val quote = repository.getQuote()
                _state.value = QuoteUiState(
                    text = quote.q,
                    author = quote.a,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = QuoteUiState(
                    text = "Small steps every day lead to big results.",
                    author = "Daynergy",
                    isLoading = false
                )
            }
        }
    }
}
