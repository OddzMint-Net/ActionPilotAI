package com.oddzmint.actionpilotai.presentation.chat.model

import com.oddzmint.actionpilotai.domain.model.AIAction

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val action: AIAction? = null
)