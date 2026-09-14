package com.oddzmint.actionpilotai.domain.model

data class AIAction(
    val type: ActionType,
    val data: Map<String, String> = emptyMap()
)