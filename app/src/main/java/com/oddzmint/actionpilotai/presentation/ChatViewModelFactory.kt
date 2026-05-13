package com.oddzmint.actionpilotai.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.oddzmint.actionpilotai.data.ai.GeminiService


object ChatViewModelFactory {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            ChatViewModel(aiActionService = GeminiService())
        }
    }
}