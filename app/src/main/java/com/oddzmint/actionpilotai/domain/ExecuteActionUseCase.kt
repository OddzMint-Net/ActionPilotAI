package com.oddzmint.actionpilotai.domain

import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import javax.inject.Inject

class ExecuteActionUseCase @Inject constructor(
    handlers: Set<@JvmSuppressWildcards ActionHandler>
) {
    private val handlersByType = handlers.associateBy { it.type }
    operator fun invoke(action: AIAction): ActionResult {
        val handler = handlersByType[action.type]
            ?: return ActionResult.Failure.Unexpected("No handler registered for ${action.type}")
        return handler.execute(action)
    }
}