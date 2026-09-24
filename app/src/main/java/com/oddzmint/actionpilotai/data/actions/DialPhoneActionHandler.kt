/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.data.actions

import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.domain.model.ActionType
import javax.inject.Inject

class DialPhoneActionHandler @Inject constructor(
    private val intentLauncher: IntentLauncher
) : ActionHandler {

    override val type: ActionType = ActionType.DIAL_PHONE

    override fun execute(action: AIAction): ActionResult {
        val phoneNumber = action.data["phoneNumber"] ?: return ActionResult.Failure.MissingData("phoneNumber")
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:${Uri.encode(phoneNumber)}".toUri()
        }
        return intentLauncher.launch(intent)
    }
}