package com.oddzmint.actionpilotai.domain.handlers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.oddzmint.actionpilotai.data.model.AIAction
import com.oddzmint.actionpilotai.data.model.ActionType
import com.oddzmint.actionpilotai.domain.ActionHandler

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
            data = Uri.parse("tel:\${Uri.encode(phoneNumber)}")
        }
        context.startActivity(intent)
    }
}