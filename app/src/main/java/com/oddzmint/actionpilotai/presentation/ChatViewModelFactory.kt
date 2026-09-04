package com.oddzmint.actionpilotai.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.oddzmint.actionpilotai.data.AIActionRepositoryImpl
import com.oddzmint.actionpilotai.data.ai.AIActionService
import com.oddzmint.actionpilotai.data.ai.GeminiService
import com.oddzmint.actionpilotai.domain.AIActionRepository
import com.oddzmint.actionpilotai.domain.GetAiActionUseCase
import com.oddzmint.actionpilotai.presentation.chat.ChatReducer

object ChatViewModelFactory {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val aiActionService: AIActionService = GeminiService()
            val repository: AIActionRepository = AIActionRepositoryImpl(aiActionService)
            val getAiActionUseCase = GetAiActionUseCase(repository)
            ChatViewModel(getAiActionUseCase, reducer = ChatReducer())
        }
    }
}