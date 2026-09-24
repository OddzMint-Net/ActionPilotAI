/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.data.ai

fun interface AIActionService {
    suspend fun getAction(userInput: String): String
}