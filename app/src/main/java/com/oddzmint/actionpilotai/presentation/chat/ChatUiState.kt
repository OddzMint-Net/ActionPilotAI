/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.presentation.chat

data class ChatUiState(
    val message: List<ChatMessage> = emptyList(),
    val userInput: String = "",
    val isLoading: Boolean = false,
    val isListening: Boolean = false,
    val error: String? = null
)