package com.oddzmint.actionpilotai.domain.usecase

import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.repository.AIActionRepository

open class GetAiActionUseCase(
    private val repository: AIActionRepository
) {
    suspend operator fun invoke(userInput: String): AIAction {
        return repository.getAction(userInput)
    }
}