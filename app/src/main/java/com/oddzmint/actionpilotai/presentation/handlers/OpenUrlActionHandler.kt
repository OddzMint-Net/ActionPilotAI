package com.oddzmint.actionpilotai.presentation.handlers

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.presentation.ActionHandler

class OpenUrlActionHandler() : ActionHandler {

    override val type: ActionType = ActionType.OPEN_URL

    override fun execute(
        context: Context,
        action: AIAction
    ) {
        val url = action.data["url"].orEmpty()
        if (url.isBlank()) {
            Toast.makeText(context, "No URL provided", Toast.LENGTH_SHORT).show()
            return
        }
        val safeUri = if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }
        val intent = Intent(Intent.ACTION_VIEW, safeUri.toUri())
        context.startActivity(intent)
    }
}