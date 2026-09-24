/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.presentation.chat

import com.oddzmint.actionpilotai.domain.model.AIAction

sealed interface ChatResult {
    data class AiSuccess(val action: AIAction, val requiresConfirmation: Boolean) : ChatResult
    data class AiFailure(val message: String) : ChatResult
    data class ActionOutcome(val message: String, val isError: Boolean) : ChatResult
}