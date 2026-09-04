package com.oddzmint.actionpilotai.data

import com.oddzmint.actionpilotai.data.ai.AIActionService
import com.oddzmint.actionpilotai.data.ai.ActionParser
import com.oddzmint.actionpilotai.domain.AIActionRepository
import com.oddzmint.actionpilotai.domain.model.AIAction

class AIActionRepositoryImpl(
    private val aiActionService: AIActionService
) : AIActionRepository {

    override suspend fun getAction(userInput: String): AIAction {
        val response = aiActionService.getAction(userInput)
        return ActionParser.parse(response)
    }
}