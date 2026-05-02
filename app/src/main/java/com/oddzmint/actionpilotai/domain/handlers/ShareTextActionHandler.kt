package com.oddzmint.actionpilotai.domain.handlers

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.oddzmint.actionpilotai.data.model.AIAction
import com.oddzmint.actionpilotai.data.model.ActionType
import com.oddzmint.actionpilotai.domain.ActionHandler

class ShareTextActionHandler() : ActionHandler {

    override val type: ActionType = ActionType.SHARE_TEXT

    override fun execute(
        context: Context,
        action: AIAction
    ) {
        val text = action.data["text"].orEmpty()
        if (text.isBlank()) {
            Toast.makeText(context, "No text to share", Toast.LENGTH_SHORT).show()
            return
        }

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_INTENT, text)
        }

        val chooser = Intent.createChooser(sendIntent, "Share with")
        context.startActivity(chooser)

    }
}