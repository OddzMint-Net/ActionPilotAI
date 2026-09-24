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

class SearchWebActionHandler @Inject constructor(
    private val intentLauncher: IntentLauncher
) : ActionHandler {

    override val type: ActionType = ActionType.SEARCH_WEB

    override fun execute(action: AIAction): ActionResult {

        val query = action.data["query"] ?: return ActionResult.Failure.MissingData("query")
        val uri = "https://www.google.com/search?q=${Uri.encode(query)}".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        return intentLauncher.launch(intent)
    }
}