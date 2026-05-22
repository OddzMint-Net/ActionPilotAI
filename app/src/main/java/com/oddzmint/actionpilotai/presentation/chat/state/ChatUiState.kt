package com.oddzmint.actionpilotai.presentation.chat.state

import com.oddzmint.actionpilotai.presentation.chat.model.ChatMessage

data class ChatUiState(
    val message: List<ChatMessage> = emptyList(),
    val userInput: String = "",
    val isLoading: Boolean = false,
    val isListening: Boolean = false,
    val error: String? = null
)