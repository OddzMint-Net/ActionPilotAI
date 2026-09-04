package com.oddzmint.actionpilotai.presentation.chat

import com.oddzmint.actionpilotai.domain.model.AIAction

sealed interface ChatResult {

    data class AiSuccess(
        val action: AIAction,
        val requiresConfirmation: Boolean
    ) : ChatResult

    data class AiFailure(
        val message: String
    ) : ChatResult
}