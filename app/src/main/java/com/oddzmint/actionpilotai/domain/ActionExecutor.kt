package com.oddzmint.actionpilotai.domain

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import com.oddzmint.actionpilotai.data.model.AIAction
import com.oddzmint.actionpilotai.data.model.ActionType

object ActionExecutor {
    fun execute(
        context: Context,
        action: AIAction
    ) {
        when (action.type) {
            ActionType.CREATE_EVENT -> createCalendarEvent(context, action)
            ActionType.OPEN_MAPS -> openMaps(context, action)
            ActionType.GENERATE_REPLY -> copyReply(context, action)
            ActionType.UNKNOWN -> {
                Toast.makeText(context, "Unknown action", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createCalendarEvent(
        context: Context,
        action: AIAction
    ) {
        val title = action.data["title"].orEmpty()

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, "Created by ActionPilotAI")
        }
        context.startActivity(intent)
    }

    private fun openMaps(
        context: Context,
        action: AIAction
    ) {
        val location = action.data["location"].orEmpty()
        val uri = Uri.parse("geo:0,0?q=${Uri.encode(location)}")

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(fallbackIntent)
        }
    }

    private fun copyReply(
        context: Context,
        action: AIAction
    ) {
        val message = action.data["message"].orEmpty()

        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE)
                as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText(
            "ActionPilotAI Reply", message
        )
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Reply copied", Toast.LENGTH_SHORT).show()
    }
}