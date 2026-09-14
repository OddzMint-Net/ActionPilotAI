package com.oddzmint.actionpilotai.data.actions

import android.content.Intent
import android.provider.CalendarContract
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.domain.model.ActionType
import javax.inject.Inject

class CreateEventActionHandler @Inject constructor(
    private val intentLauncher: IntentLauncher
) : ActionHandler {

    override val type: ActionType = ActionType.CREATE_EVENT

    override fun execute(action: AIAction): ActionResult {
        val title = action.data["title"] ?: return ActionResult.Failure.MissingData("title")

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, "Created by ActionPilotAI")
        }
        return intentLauncher.launch(intent)
    }
}