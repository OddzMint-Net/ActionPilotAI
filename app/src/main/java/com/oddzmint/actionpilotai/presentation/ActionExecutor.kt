package com.oddzmint.actionpilotai.presentation

import android.content.Context
import android.widget.Toast
import com.oddzmint.actionpilotai.presentation.handlers.CreateEventActionHandler
import com.oddzmint.actionpilotai.presentation.handlers.DialPhoneActionHandler
import com.oddzmint.actionpilotai.presentation.handlers.GenerateReplyActionHandler
import com.oddzmint.actionpilotai.presentation.handlers.OpenMapsActionHandler
import com.oddzmint.actionpilotai.presentation.handlers.OpenUrlActionHandler
import com.oddzmint.actionpilotai.presentation.handlers.SearchWebActionHandler
import com.oddzmint.actionpilotai.presentation.handlers.ShareTextActionHandler
import com.oddzmint.actionpilotai.domain.model.AIAction

object ActionExecutor {
    private val handlers: List<ActionHandler> = listOf(
        CreateEventActionHandler(),
        OpenMapsActionHandler(),
        GenerateReplyActionHandler(),
        SearchWebActionHandler(),
        ShareTextActionHandler(),
        DialPhoneActionHandler(),
        OpenUrlActionHandler()
    )

    fun execute(
        context: Context,
        action: AIAction
    ) {
        val handler = handlers.firstOrNull { it.type == action.type }
        if (handler == null) {
            Toast.makeText(context, "unknown action", Toast.LENGTH_SHORT).show()
            return
        }
        handler.execute(context, action)
    }
}