/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.data.actions

import android.content.ClipData
import android.content.ClipboardManager
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.domain.model.ActionType
import javax.inject.Inject

class GenerateReplyActionHandler @Inject constructor(
    private val clipboardManager: ClipboardManager
) : ActionHandler {

    override val type: ActionType = ActionType.GENERATE_REPLY

    override fun execute(action: AIAction): ActionResult {
        val message = action.data["message"] ?: return ActionResult.Failure.MissingData("message")
        return try {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("ActionPilotAI Reply", message))
            ActionResult.Success
        } catch (e: Exception) {
            ActionResult.Failure.Unexpected(e.message ?: "Unknown error copying reply")
        }
    }
}