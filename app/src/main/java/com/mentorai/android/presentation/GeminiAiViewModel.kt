package com.mentorai.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentorai.android.domain.GeminiAiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeminiAiViewModel @Inject constructor(
    val geminiAiRepository: GeminiAiRepository
) : ViewModel() {

    private val _responseState: MutableStateFlow<GeminiAiResponseState> =
        MutableStateFlow(GeminiAiResponseState.Idle)
    val responseState: StateFlow<GeminiAiResponseState> = _responseState.asStateFlow()


    fun generateContent(query: String) {
        viewModelScope.launch {
            _responseState.value = GeminiAiResponseState.Loading
            val response = geminiAiRepository.generateContent(query)
            val result = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
            if (result?.isNotEmpty() == true) {
                _responseState.value = GeminiAiResponseState.Success(result = result)
            } else {
                _responseState.value = GeminiAiResponseState.Error(message = "No Response")
            }
        }
    }

    sealed class GeminiAiResponseState {
        data object Idle : GeminiAiResponseState()
        data object Loading : GeminiAiResponseState()
        data class Success(val result: String) : GeminiAiResponseState()
        data class Error(val message: String) : GeminiAiResponseState()
    }
}
