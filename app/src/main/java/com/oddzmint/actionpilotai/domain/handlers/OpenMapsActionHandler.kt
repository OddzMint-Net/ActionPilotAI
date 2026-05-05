package com.oddzmint.actionpilotai.domain.handlers

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.oddzmint.actionpilotai.data.model.AIAction
import com.oddzmint.actionpilotai.data.model.ActionType
import com.oddzmint.actionpilotai.domain.ActionHandler

class OpenMapsActionHandler : ActionHandler {
    override val type: ActionType = ActionType.OPEN_MAPS

    override fun execute(
        context: Context,
        action: AIAction
    ) {
        val uri = "geo:0,0?q=\${Uri.encode(location)}".toUri()
        val mapsIntent = Intent(Intent.ACTION_VIEW,uri).apply {
            setPackage("com.google.android.apps.maps")
        }
        try {
            context.startActivity(mapsIntent)
        } catch (e: Exception){
            val fallbackIntent = Intent(Intent.ACTION_VIEW,uri)
            context.startActivity(fallbackIntent)
        }
    }
}