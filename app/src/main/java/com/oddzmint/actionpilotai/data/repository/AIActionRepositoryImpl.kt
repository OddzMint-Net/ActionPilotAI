package com.oddzmint.actionpilotai.data.repository

import com.oddzmint.actionpilotai.data.ai.AIActionService
import com.oddzmint.actionpilotai.domain.ActionParser
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.repository.AIActionRepository

class AIActionRepositoryImpl(
    private val aiActionService: AIActionService
) : AIActionRepository {

    override suspend fun getAction(userInput: String): AIAction {
        val response = aiActionService.getAction(userInput)
        return ActionParser.parse(response)
    }
}