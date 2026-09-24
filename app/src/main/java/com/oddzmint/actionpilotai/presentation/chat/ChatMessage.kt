/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.presentation.chat

import com.oddzmint.actionpilotai.domain.model.AIAction

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val action: AIAction? = null,
    val isError: Boolean = false
)