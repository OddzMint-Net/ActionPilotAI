package com.oddzmint.actionpilotai.data.actions

import android.content.Context
import android.content.Intent
import com.oddzmint.actionpilotai.domain.model.ActionResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class IntentLauncher @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun launch(intent: Intent): ActionResult {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (intent.resolveActivity(context.packageManager) == null) {
            return ActionResult.Failure.NoHandlerApp
        }
        return try {
            context.startActivity(intent)
            ActionResult.Success
        } catch (e: Exception) {
            ActionResult.Failure.Unexpected(e.message ?: "Unknown error launching intent")
        }
    }
}