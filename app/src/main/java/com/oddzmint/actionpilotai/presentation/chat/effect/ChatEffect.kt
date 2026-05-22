package com.oddzmint.actionpilotai.presentation.chat.effect

import com.oddzmint.actionpilotai.domain.model.AIAction

sealed interface ChatEffect {
    data class ExecuteAction(val action: AIAction) : ChatEffect
    data object LaunchVoiceInput : ChatEffect
}