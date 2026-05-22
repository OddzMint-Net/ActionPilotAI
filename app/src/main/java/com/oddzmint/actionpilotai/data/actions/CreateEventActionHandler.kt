package com.oddzmint.actionpilotai.data.actions

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.action.ActionHandler

class CreateEventActionHandler : ActionHandler {

    override val type: ActionType = ActionType.CREATE_EVENT

    override fun execute(
        context: Context,
        action: AIAction
    ) {
        val title = action.data["title"].orEmpty()

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, "Created by ActionPilotAI")
        }
        context.startActivity(intent)
    }
}