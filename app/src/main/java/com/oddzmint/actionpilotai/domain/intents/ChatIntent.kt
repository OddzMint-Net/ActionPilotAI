package com.oddzmint.actionpilotai.domain.intents

import com.oddzmint.actionpilotai.data.model.AIAction

sealed interface ChatIntent {
    data class InputChanged(
        val value: String
    ) : ChatIntent

    data object SendClicked : ChatIntent
    data object VoiceInputStarted : ChatIntent
    data class VoiceInputReceived(val text: String) : ChatIntent
    data object VoiceInputFailed : ChatIntent
    data class AiResponseReceived(val action: AIAction) : ChatIntent
    data class AiErrorOccurred(val message: String) : ChatIntent
    data class ConfirmAction(val action: AIAction) : ChatIntent
}