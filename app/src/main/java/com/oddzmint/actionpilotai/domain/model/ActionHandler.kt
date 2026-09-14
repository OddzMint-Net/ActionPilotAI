package com.oddzmint.actionpilotai.domain.model

interface ActionHandler {
    val type: ActionType
    fun execute(action: AIAction): ActionResult
}