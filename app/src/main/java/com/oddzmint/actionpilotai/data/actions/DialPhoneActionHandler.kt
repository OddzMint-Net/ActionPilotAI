package com.oddzmint.actionpilotai.data.actions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.action.ActionHandler

class DialPhoneActionHandler() : ActionHandler {

    override val type: ActionType = ActionType.DIAL_PHONE

    override fun execute(
        context: Context,
        action: AIAction
    ) {
        val phoneNumber = action.data["phoneNumber"].orEmpty()
        if (phoneNumber.isBlank()) {
            Toast.makeText(context, "No phone number provided", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:\${Uri.encode(phoneNumber)}".toUri()
        }
        context.startActivity(intent)
    }
}