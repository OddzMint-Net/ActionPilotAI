/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.data

import com.oddzmint.actionpilotai.data.ai.AIActionService
import com.oddzmint.actionpilotai.data.ai.ActionParser
import com.oddzmint.actionpilotai.domain.AIActionRepository
import com.oddzmint.actionpilotai.domain.model.AIAction
import javax.inject.Inject

class AIActionRepositoryImpl @Inject constructor(
    private val aiActionService: AIActionService
) : AIActionRepository {

    override suspend fun getAction(userInput: String): AIAction {
        val response = aiActionService.getAction(userInput)
        return ActionParser.parse(response)
    }
}