package com.oddzmint.actionpilotai.domain

import com.oddzmint.actionpilotai.domain.model.AIAction

open class GetAiActionUseCase(
    private val repository: AIActionRepository
) {
    suspend operator fun invoke(userInput: String): AIAction {
        return repository.getAction(userInput)
    }
}