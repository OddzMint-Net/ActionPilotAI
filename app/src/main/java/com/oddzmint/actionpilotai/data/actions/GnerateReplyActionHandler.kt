package com.oddzmint.actionpilotai.data.actions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.action.ActionHandler

class GenerateReplyActionHandler() : ActionHandler {

    override val type: ActionType = ActionType.GENERATE_REPLY

    override fun execute(
        context: Context,
        action: AIAction
    ) {
        val message = action.data["message"].orEmpty()
        if (message.isBlank()) {
            Toast.makeText(context, "No reply generated", Toast.LENGTH_SHORT).show()
            return
        }
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("ActionPilotAI Reply", message)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Reply copied", Toast.LENGTH_SHORT).show()
        return
    }
}