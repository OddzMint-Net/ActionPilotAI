package com.oddzmint.actionpilotai.data.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.domain.model.ActionType
import javax.inject.Inject

class OpenMapsActionHandler @Inject constructor(
    private val intentLauncher: IntentLauncher
) : ActionHandler {
    override val type: ActionType = ActionType.OPEN_MAPS

    override fun execute(action: AIAction): ActionResult {
        val location = action.data["location"] ?: return ActionResult.Failure.MissingData("location")
        val uri = "geo:0,0?q=${Uri.encode(location)}".toUri()
        val mapsIntent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }

        val result = intentLauncher.launch(mapsIntent)
        return if (result is ActionResult.Failure.NoHandlerApp) {
            intentLauncher.launch(Intent(Intent.ACTION_VIEW, uri))
        } else {
            result
        }
    }
}