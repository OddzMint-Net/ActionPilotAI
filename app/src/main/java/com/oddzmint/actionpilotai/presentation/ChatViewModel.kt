package com.oddzmint.actionpilotai.presentation

import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.actionpilotai.data.model.ChatMessage
import com.oddzmint.actionpilotai.domain.AIActionService
import com.oddzmint.actionpilotai.domain.ActionParser
import com.oddzmint.actionpilotai.domain.effect.ChatEffect
import com.oddzmint.actionpilotai.domain.intents.ChatIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val aiActionService: AIActionService
) : ViewModel() {

    // --State--
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    // --Effects--
    private val _effects = Channel<ChatEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    //-- Single entry point
    fun onIntent(intent: ChatIntent) {
        val inputSnapshot = when (intent) {
            is ChatIntent.SendClicked -> _uiState.value.userInput.trim()
            is ChatIntent.VoiceInputReceived -> intent.text.trim()
            else -> null
            }

        _uiState.value = reduce(_uiState.value, intent)
        handleEffect(intent, inputSnapshot)
    }

    // --Reducer -- pure, no coroutines, no API calls--

    private fun reduce(current: ChatUiState, intent: ChatIntent): ChatUiState {
        return when (intent) {
            is ChatIntent.InputChanged -> current.copy(
                userInput = intent.value
            )

            is ChatIntent.SendClicked -> {
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

            is ChatIntent.VoiceInputStarted -> current.copy(
                isListening = true,
                error = null
            )

            is ChatIntent.VoiceInputReceived -> {
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

            is ChatIntent.VoiceInputFailed -> current.copy(
                isListening = false,
                error = "Could not recognize voice. Please try again"
            )

            is ChatIntent.AiResponseReceived -> current.copy(
                message = current.message + ChatMessage(
                    text = "I found an action for you",
                    isFromUser = false,
                    action = intent.action
                ),
                isLoading = false,
            )

            is ChatIntent.AiErrorOccurred -> current.copy(
                message = current.message + ChatMessage(
                    text = "Something went wrong. Please try again",
                    isFromUser = false
                ),
                isLoading = false,
            )

            is ChatIntent.ConfirmAction -> current
        }
    }

    private fun handleEffect(intent: ChatIntent, inputSnapshot: String? = null) {
        when (intent) {
            is ChatIntent.SendClicked -> {
                if (inputSnapshot.isNullOrBlank()) return
                viewModelScope.launch { callAiService(inputSnapshot) }
            }

            is ChatIntent.VoiceInputStarted -> {
                viewModelScope.launch {
                    _effects.send(ChatEffect.LaunchVoiceInput)
                }
            }

            is ChatIntent.VoiceInputReceived -> {
                if (inputSnapshot.isNullOrBlank()) return
                viewModelScope.launch { callAiService(inputSnapshot) }
            }

            is ChatIntent.ConfirmAction -> {
                viewModelScope.launch {
                    _effects.send(ChatEffect.ExecuteAction(intent.action))
                }
            }
            else -> Unit
        }
    }

    private suspend fun callAiService(input: String) {
        try {
            val aiResponse = aiActionService.getAction(input)
            val action = ActionParser.parse(aiResponse)
            onIntent(ChatIntent.AiResponseReceived(action))
        } catch (e: Exception) {
            onIntent(ChatIntent.AiErrorOccurred("Something went wrong. Please try again later"))
        }
    }
}


//    private fun sendPrompt() {
//        val input = _uiState.value.userInput.trim()
//        if (input.isBlank()) return
//        val userMessage = ChatMessage(
//            text = input,
//            isFromUser = true
//        )
//
//        _uiState.update {
//            it.copy(
//                message = it.message + userMessage,
//                userInput = "",
//                isLoading = true
//            )
//        }
//
//        viewModelScope.launch {
//            try {
//                val aiResponse = aiActionService.getAction(input)
//                val action = ActionParser.parse(aiResponse)
//                val aiMessage = ChatMessage(
//                    text = "I found an action for you",
//                    isFromUser = false,
//                    action = action
//                )
//                _uiState.update {
//                    it.copy(
//                        message = it.message + aiMessage,
//                        isLoading = false
//                    )
//                }
//            } catch (e: Exception) {
//                val errorMessage = ChatMessage(
//                    text = "Something went wrong. Please try again.",
//                    isFromUser = false
//                )
//                _uiState.update {
//                    it.copy(
//                        message = it.message + errorMessage,
//                        isLoading = false
//                    )
//                }
//            }
//        }
//    }
//}