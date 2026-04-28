package com.oddzmint.actionpilotai.data.model

data class AIAction(
    val type: ActionType,
    val data: Map<String, String> = emptyMap()
)
