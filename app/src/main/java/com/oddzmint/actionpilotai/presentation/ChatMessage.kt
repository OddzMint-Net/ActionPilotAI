package com.oddzmint.actionpilotai.presentation

import com.oddzmint.actionpilotai.domain.model.AIAction

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val action: AIAction? = null
)