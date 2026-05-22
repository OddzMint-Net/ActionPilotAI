package com.oddzmint.actionpilotai.presentation.chat.results

import com.oddzmint.actionpilotai.domain.model.AIAction

sealed interface ChatResult {

    data class AiSuccess(
        val action: AIAction
    ) : ChatResult

    data class AiFailure(
        val message: String
    ) : ChatResult
}