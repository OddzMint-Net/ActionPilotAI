package com.oddzmint.actionpilotai.domain.handlers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import com.oddzmint.actionpilotai.data.model.AIAction
import com.oddzmint.actionpilotai.data.model.ActionType
import com.oddzmint.actionpilotai.domain.ActionHandler

class SearchWebActionHandler() : ActionHandler {

    override val type: ActionType = ActionType.SEARCH_WEB

    override fun execute(
        context: Context,
        action: AIAction
    ) {
        val query = action.data["query"].orEmpty()
        if (query.isBlank()) {
            Toast.makeText(context, "No search query provided", Toast.LENGTH_SHORT).show()
            return
        }
        val uri = "https://www.google.com/search?q=${Uri.encode(query)}".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}