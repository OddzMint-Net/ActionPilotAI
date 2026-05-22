package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.presentation.chat.intent.ChatIntent
import com.oddzmint.actionpilotai.presentation.chat.reducer.ChatReducer
import com.oddzmint.actionpilotai.presentation.chat.results.ChatResult
import com.oddzmint.actionpilotai.presentation.chat.state.ChatUiState
import com.oddzmint.actionpilotai.presentation.chat.viewmodel.ChatViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ChatReducerTest {
    private val reducer = ChatReducer()

    @Test
    fun `UpdateInput updates user input`() {
        val state = ChatUiState()
        val result = reducer(
            state,
            ChatIntent.UpdateInput("Schedule meeting")
        )
        assertEquals("Schedule meeting", result.userInput)
    }

    @Test
    fun `SendMessage adds user and clears input`() {
        val state = ChatUiState(
            userInput = "Schedule meeting tomorrow"
        )

        val result = reducer(
            state,
            ChatIntent.SendMessage
        )
        assertEquals("", result.userInput)
        assertTrue(result.isLoading)
        assertEquals(1, result.message.size)
        assertEquals("Schedule meeting tomorrow", result.message.first().text)
        assertTrue(result.message.first().isFromUser)
    }

    @Test
    fun `SendMessage does nothing when input is blank`() {
        val state = ChatUiState(
            userInput = " "
        )

        val result = reducer(
            state,
            ChatIntent.SendMessage
        )
        assertEquals(state, result)
    }

    @Test
    fun `StartVoiceInput enables listening`() {
        val state = ChatUiState()
        val result = reducer(
            state,
            ChatIntent.StartVoiceInput
        )
        assertTrue(result.isListening)
        assertNull(result.error)
    }

    @Test
    fun `SubmitVoiceInput adds message and start loading`() {
        val state = ChatUiState(
            isListening = true
        )
        val result = reducer(
            state,
            ChatIntent.SubmitVoiceInput("Call Odwa")
        )
        assertFalse(result.isListening)
        assertTrue(result.isLoading)
        assertEquals(1, result.message.size)
        assertEquals("Call Odwa", result.message.first().text)
    }

    @Test
    fun `VoiceRecognitionFailed sets error`() {
        val state = ChatUiState(
            isListening = true
        )
        val result = reducer(
            state,
            ChatIntent.VoiceRecognitionFailed
        )
        assertFalse(result.isListening)
        assertNotNull(result.error)
    }

    @Test
    fun `AiSuccess adds assistant message`() {
        val state = ChatUiState(
            isLoading = true
        )
        val action = AIAction(
            type = ActionType.CREATE_EVENT
        )
        val result = reducer(
            state,
            ChatResult.AiSuccess(action)
        )
        assertFalse(result.isLoading)
        assertEquals(1, result.message.size)
        assertFalse(result.message.first().isFromUser)
        assertEquals(action, result.message.first().action)
    }

    @Test
    fun `AiFailure shows error message`() {
        val state = ChatUiState(
            isLoading = true
        )
        val result = reducer(
            state,
            ChatResult.AiFailure("Error")
        )
        assertFalse(result.isLoading)
        assertEquals(1, result.message.size)
        assertEquals(ChatViewModel.ERROR_MESSAGE, result.message.first().text)
    }
}