package com.oddzmint.actionpilotai.domain.action

import android.content.Context
import android.widget.Toast
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.data.actions.CreateEventActionHandler
import com.oddzmint.actionpilotai.data.actions.DialPhoneActionHandler
import com.oddzmint.actionpilotai.data.actions.GenerateReplyActionHandler
import com.oddzmint.actionpilotai.data.actions.OpenMapsActionHandler
import com.oddzmint.actionpilotai.data.actions.OpenUrlActionHandler
import com.oddzmint.actionpilotai.data.actions.SearchWebActionHandler
import com.oddzmint.actionpilotai.data.actions.ShareTextActionHandler

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