package com.oddzmint.actionpilotai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.actionpilotai.data.model.ChatMessage
import com.oddzmint.actionpilotai.domain.AIActionService
import com.oddzmint.actionpilotai.domain.ActionParser
import com.oddzmint.actionpilotai.domain.effect.ChatEffect
import com.oddzmint.actionpilotai.domain.intents.ChatIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val aiActionService: AIActionService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<ChatEffect>()
    val effects = _effects.asSharedFlow()

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.InputChanged -> {
                _uiState.update {
                    it.copy(userInput = intent.value)
                }
            }

            ChatIntent.SendClicked -> {
                sendPrompt()
            }

            is ChatIntent.ConfirmAction -> {
                viewModelScope.launch {
                    _effects.emit(
                        ChatEffect.ExecuteAction(intent.action)
                    )
                }
            }
        }
    }

    private fun sendPrompt() {
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