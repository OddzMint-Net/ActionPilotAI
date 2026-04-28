package com.oddzmint.actionpilotai.data.model

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val action: AIAction? = null
)
