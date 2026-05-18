package com.oddzmint.actionpilotai.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.oddzmint.actionpilotai.data.ai.AIActionService
import com.oddzmint.actionpilotai.data.ai.GeminiService
import com.oddzmint.actionpilotai.data.repository.AIActionRepositoryImpl
import com.oddzmint.actionpilotai.domain.repository.AIActionRepository
import com.oddzmint.actionpilotai.domain.usecase.GetAiActionUseCase

object ChatViewModelFactory {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val aiActionService: AIActionService = GeminiService()
            val repository: AIActionRepository = AIActionRepositoryImpl(aiActionService)
            val getAiActionUseCase = GetAiActionUseCase(repository)
            ChatViewModel(getAiActionUseCase)
        }
    }
}