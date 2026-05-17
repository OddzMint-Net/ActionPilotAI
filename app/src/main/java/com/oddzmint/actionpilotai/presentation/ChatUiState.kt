package com.oddzmint.actionpilotai.presentation

import com.oddzmint.actionpilotai.data.model.ChatMessage

data class ChatUiState(
    val message: List<ChatMessage> = emptyList(),
    val userInput: String = "",
    val isLoading: Boolean = false,
    val isListening: Boolean = false,
    val error: String? = null
)
