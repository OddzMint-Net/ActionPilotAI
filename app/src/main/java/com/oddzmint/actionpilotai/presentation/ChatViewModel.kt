package com.oddzmint.actionpilotai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.actionpilotai.domain.GetAiActionUseCase
import com.oddzmint.actionpilotai.domain.extensions.requiresConfirmation
import com.oddzmint.actionpilotai.presentation.chat.ChatEffect
import com.oddzmint.actionpilotai.presentation.chat.ChatIntent
import com.oddzmint.actionpilotai.presentation.chat.ChatReducer
import com.oddzmint.actionpilotai.presentation.chat.ChatResult
import com.oddzmint.actionpilotai.presentation.chat.ChatUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getAiActionUseCase: GetAiActionUseCase,
    private val reducer: ChatReducer
) : ViewModel() {

    companion object {
        const val ERROR_MESSAGE = "Something went wrong. Please try again."
    }
    // --State--
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    // --Effects--
    private val _effects = Channel<ChatEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    //-- Single entry point
    fun onIntent(intent: ChatIntent) {
        val inputSnapshot = when (intent) {
            is ChatIntent.SendMessage -> _uiState.value.userInput.trim()
            is ChatIntent.SubmitVoiceInput -> intent.text.trim()
            else -> null
        }

        _uiState.value = reducer(_uiState.value, intent)
        handleEffect(intent, inputSnapshot)
    }

    // --Reducer -- pure, no coroutines, no API calls--
    private fun handleEffect(intent: ChatIntent, inputSnapshot: String? = null) {
        when (intent) {
            is ChatIntent.SendMessage,
            is ChatIntent.SubmitVoiceInput -> {
                if (inputSnapshot.isNullOrBlank()) return
                viewModelScope.launch { callAiService(inputSnapshot) }
            }

            is ChatIntent.StartVoiceInput -> {
                viewModelScope.launch {
                    _effects.send(ChatEffect.LaunchVoiceInput)
                }
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
            val action = getAiActionUseCase(input)
            val requiresConfirmation = action.type.requiresConfirmation()

            if (!requiresConfirmation) {
                _effects.send(ChatEffect.ExecuteAction(action))
            }
            applyResult(
                ChatResult.AiSuccess(
                    action = action,
                    requiresConfirmation = requiresConfirmation
                )
            )
        } catch (e: Exception) {
            applyResult(ChatResult.AiFailure(ERROR_MESSAGE))
        }
    }

    private fun applyResult(
        result: ChatResult
    ) {
        _uiState.value = reducer(_uiState.value, result)
    }
}