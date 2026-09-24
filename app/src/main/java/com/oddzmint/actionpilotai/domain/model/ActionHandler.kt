/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.domain.model

interface ActionHandler {
    val type: ActionType
    fun execute(action: AIAction): ActionResult
}