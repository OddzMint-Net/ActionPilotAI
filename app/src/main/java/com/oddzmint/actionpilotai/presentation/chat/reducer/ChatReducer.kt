package com.oddzmint.actionpilotai.presentation.chat.reducer

import com.oddzmint.actionpilotai.presentation.chat.intent.ChatIntent
import com.oddzmint.actionpilotai.presentation.chat.model.ChatMessage
import com.oddzmint.actionpilotai.presentation.chat.results.ChatResult
import com.oddzmint.actionpilotai.presentation.chat.state.ChatUiState
import com.oddzmint.actionpilotai.presentation.chat.viewmodel.ChatViewModel.Companion.ERROR_MESSAGE

class ChatReducer {

    operator fun invoke(
        current: ChatUiState,
        intent: ChatIntent
    ): ChatUiState {
        return when (intent) {
            is ChatIntent.UpdateInput -> current.copy(
                userInput = intent.value
            )

            is ChatIntent.SendMessage -> {
                if (current.userInput.isBlank()) return current
                val userMessage = ChatMessage(
                    text = current.userInput.trim(),
                    isFromUser = true
                )
                current.copy(
                    message = current.message + userMessage,
                    userInput = "",
                    isLoading = true,
                )
            }

            is ChatIntent.StartVoiceInput -> current.copy(
                isListening = true,
                error = null
            )

            is ChatIntent.SubmitVoiceInput -> {
                val userMessage = ChatMessage(
                    text = intent.text.trim(),
                    isFromUser = true
                )
                current.copy(
                    isListening = false,
                    message = current.message + userMessage,
                    userInput = "",
                    isLoading = true
                )
            }

            is ChatIntent.VoiceRecognitionFailed -> current.copy(
                isListening = false,
                error = "Could not recognize voice. Please try again"
            )

            is ChatIntent.ConfirmAction -> current
        }
    }

    operator fun invoke(
        current: ChatUiState,
        result: ChatResult
    ): ChatUiState {

        return when (result) {
            is ChatResult.AiSuccess -> current.copy(
                message = current.message + ChatMessage(
                    text = "I found an action for you",
                    isFromUser = false,
                    action = result.action
                ),
                isLoading = false
            )

            is ChatResult.AiFailure -> current.copy(
                message = current.message + ChatMessage(
                    text = ERROR_MESSAGE,
                    isFromUser = false
                ),
                isLoading = false,
            )
        }
    }
}