package com.oddzmint.actionpilotai.domain.intents

import com.oddzmint.actionpilotai.data.model.AIAction

sealed interface ChatIntent {
    data class InputChanged(
        val value: String
    ) : ChatIntent

    data object SendClicked : ChatIntent
    data class ConfirmAction(val action: AIAction) : ChatIntent
}