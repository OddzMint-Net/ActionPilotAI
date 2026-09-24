/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.presentation.chat

import com.oddzmint.actionpilotai.domain.model.AIAction

sealed interface ChatIntent {
    data class UpdateInput(
        val value: String
    ) : ChatIntent

    data object SendMessage : ChatIntent
    data object StartVoiceInput : ChatIntent
    data class SubmitVoiceInput(val text: String) : ChatIntent
    data object VoiceRecognitionFailed : ChatIntent
    data class ConfirmAction(val action: AIAction) : ChatIntent
}