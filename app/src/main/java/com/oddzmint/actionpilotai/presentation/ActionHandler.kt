package com.oddzmint.actionpilotai.presentation

import android.content.Context
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType

interface ActionHandler {
    val type: ActionType

    fun execute(
        context: Context,
        action: AIAction
    )
}