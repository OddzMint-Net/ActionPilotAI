package com.oddzmint.actionpilotai.domain

import com.oddzmint.actionpilotai.domain.model.AIAction
import javax.inject.Inject

 open class GetAiActionUseCase @Inject constructor(
    private val repository: AIActionRepository
) {
    suspend operator fun invoke(userInput: String): AIAction {
        return repository.getAction(userInput)
    }
}