package com.oddzmint.actionpilotai.domain.effect

import com.oddzmint.actionpilotai.data.model.AIAction

sealed interface ChatEffect {
    data class ExecuteAction(
        val action: AIAction
    ) : ChatEffect
}