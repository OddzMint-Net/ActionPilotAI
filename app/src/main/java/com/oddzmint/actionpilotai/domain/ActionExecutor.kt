package com.oddzmint.actionpilotai.domain

import android.content.Context
import android.widget.Toast
import com.oddzmint.actionpilotai.data.model.AIAction
import com.oddzmint.actionpilotai.domain.handlers.CreateEventActionHandler
import com.oddzmint.actionpilotai.domain.handlers.DialPhoneActionHandler
import com.oddzmint.actionpilotai.domain.handlers.GenerateReplyActionHandler
import com.oddzmint.actionpilotai.domain.handlers.OpenMapsActionHandler
import com.oddzmint.actionpilotai.domain.handlers.OpenUrlActionHandler
import com.oddzmint.actionpilotai.domain.handlers.SearchWebActionHandler
import com.oddzmint.actionpilotai.domain.handlers.ShareTextActionHandler

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