/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.domain

import com.oddzmint.actionpilotai.domain.model.AIAction

fun interface AIActionRepository {
    suspend fun getAction(userInput: String): AIAction
}