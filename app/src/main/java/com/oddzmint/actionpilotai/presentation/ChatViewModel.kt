package com.oddzmint.actionpilotai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.actionpilotai.data.ai.GeminiService
import com.oddzmint.actionpilotai.data.model.ChatMessage
import com.oddzmint.actionpilotai.domain.AIActionService
import com.oddzmint.actionpilotai.domain.ActionParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val aiActionService: AIActionService = GeminiService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun onInputChange(value: String) {
        _uiState.update {
            it.copy(userInput = value)
        }
    }

    fun onSendClick() {
        val input = _uiState.value.userInput.trim()
        if (input.isBlank()) return
        val userMessage = ChatMessage(
            text = input,
            isFromUser = true
        )
        _uiState.update {
            it.copy(
                message = it.message + userMessage,
                userInput = "",
                isLoading = true
            )
        }

        viewModelScope.launch {
            try {
                val aiResponse = aiActionService.getAction(input)
                val action = ActionParser.parse(aiResponse)

                val aiMessage = ChatMessage(
                    text = "I found an action for you",
                    isFromUser = false,
                    action = action
                )
                _uiState.update {
                    it.copy(
                        message = it.message + aiMessage,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                val errorMessage = ChatMessage(
                    text = "Something went wrong. Please try again.",
                    isFromUser = false
                )
                _uiState.update {
                    it.copy(
                        message = it.message + errorMessage,
                        isLoading = false
                    )
                }
            }
        }
    }
}
