package com.oddzmint.actionpilotai.domain.model

import com.oddzmint.actionpilotai.domain.action.ActionType

data class AIAction(
    val type: ActionType,
    val data: Map<String, String> = emptyMap()
)