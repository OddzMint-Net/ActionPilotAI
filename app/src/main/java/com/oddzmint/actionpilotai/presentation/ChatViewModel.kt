package com.oddzmint.actionpilotai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.actionpilotai.domain.ExecuteActionUseCase
import com.oddzmint.actionpilotai.domain.GetAiActionUseCase
import com.oddzmint.actionpilotai.domain.extensions.requiresConfirmation
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.presentation.chat.ChatEffect
import com.oddzmint.actionpilotai.presentation.chat.ChatIntent
import com.oddzmint.actionpilotai.presentation.chat.ChatReducer
import com.oddzmint.actionpilotai.presentation.chat.ChatResult
import com.oddzmint.actionpilotai.presentation.chat.ChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getAiActionUseCase: GetAiActionUseCase,
    private val executeActionUseCase: ExecuteActionUseCase,
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

    //Actions awaiting user confirmation, keyed by a generated id.
    // ChatUiState/ChatMessage only ever hold the id- never the domain AIAction.
    private val pendingActions = mutableMapOf<String, AIAction>()

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

            is ChatIntent.ConfirmAction -> runAction(intent.action)
            else -> Unit
        }
    }

    private suspend fun callAiService(input: String) {
        try {
            val action = getAiActionUseCase(input)
            val requiresConfirmation = action.type.requiresConfirmation()
            applyResult(
                ChatResult.AiSuccess(
                    action = action,
                    requiresConfirmation = requiresConfirmation
                )
            )
            if (!requiresConfirmation) {
                runAction(action)
            }
        } catch (e: Exception) {
            applyResult(ChatResult.AiFailure(ERROR_MESSAGE))
        }
    }

    private fun runAction(action: AIAction) {
        val result = executeActionUseCase(action)
        applyResult(
            ChatResult.ActionOutcome(
                message = result.toFeedbackMessage(),
                isError = result is ActionResult.Failure
            )
        )
    }

    private fun ActionResult.toFeedbackMessage(): String = when (this) {
        is ActionResult.Success -> "Done."
        is ActionResult.Failure.MissingData -> "Missing ${field}."
        is ActionResult.Failure.NoHandlerApp -> "No app found to handle this action"
        is ActionResult.Failure.Unexpected -> cause
    }

    private fun applyResult(result: ChatResult) {
        _uiState.value = reducer(_uiState.value, result)
    }
}