package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.domain.AIActionService

class FailingAIActionService : AIActionService {
    override suspend fun getAction(userInput: String): String {
        throw Exception("Network error")
    }
}