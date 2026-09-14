package com.oddzmint.actionpilotai.data.actions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.domain.model.ActionType
import javax.inject.Inject

class ShareTextActionHandler @Inject constructor(
    private val intentLauncher: IntentLauncher
) : ActionHandler {

    override val type: ActionType = ActionType.SHARE_TEXT

    override fun execute(action: AIAction): ActionResult {

        val text = action.data["text"] ?: return ActionResult.Failure.MissingData("text")
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        val chooser = Intent.createChooser(sendIntent, "Share with")
        return intentLauncher.launch(chooser)
    }
}