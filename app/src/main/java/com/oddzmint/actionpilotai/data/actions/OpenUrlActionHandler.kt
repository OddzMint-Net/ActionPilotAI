package com.oddzmint.actionpilotai.data.actions

import android.content.Intent
import androidx.core.net.toUri
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.domain.model.ActionType
import javax.inject.Inject

class OpenUrlActionHandler @Inject constructor(
    private val intentLauncher: IntentLauncher
) : ActionHandler {

    override val type: ActionType = ActionType.OPEN_URL

    override fun execute(action: AIAction): ActionResult {

        val url = action.data["url"] ?: return ActionResult.Failure.MissingData("url")
        val safeUri = if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }

        val intent = Intent(Intent.ACTION_VIEW, safeUri.toUri())
        return intentLauncher.launch(intent)
    }
}