package com.oddzmint.actionpilotai.domain.action

import android.content.Context
import com.oddzmint.actionpilotai.domain.model.AIAction

interface ActionHandler {
    val type: ActionType

    fun execute(
        context: Context,
        action: AIAction
    )
}