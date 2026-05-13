package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.domain.AIActionService
import com.oddzmint.actionpilotai.domain.intents.ChatIntent
import com.oddzmint.actionpilotai.presentation.ChatViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ChatViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeAIActionService : AIActionService {
        override suspend fun getAction(userInput: String): String {
            return """
                {
                "type":"CREATE_EVENT",
                "data": {
                "title":"Team meeting",
                "date":"2026-05-10",
                "time":"08:30"
                }
               }
            """.trimIndent()
        }
    }

    @Test
    fun `onInputChange updates user input`() {
        val viewModel = ChatViewModel(
            aiActionService = FakeAIActionService()
        )
        viewModel.onIntent(ChatIntent.InputChanged("Create meeting tomorrow"))
        assertEquals("Create meeting tomorrow", viewModel.uiState.value.userInput)
    }

    @Test
    fun `onSendClick adds user message and clears input`() = runTest {
        val viewModel = ChatViewModel(
            aiActionService = FakeAIActionService()
        )

        viewModel.onIntent(ChatIntent.InputChanged("Schedule meeting tomorrow"))
        viewModel.onIntent(ChatIntent.SendClicked)
        val state = viewModel.uiState.value
        assertEquals("", state.userInput)
        assertEquals(2, state.message.size)
        assertEquals("Schedule meeting tomorrow", state.message.first().text)

        assertTrue(state.message.first().isFromUser)
    }

    @Test
    fun `onSendClick shows error message when service fails`() = runTest {
        val viewModel = ChatViewModel(
            aiActionService = FailingAIActionService()
        )
        viewModel.onIntent(ChatIntent.InputChanged("Schedule meeting tomorrow"))
        viewModel.onIntent(ChatIntent.SendClicked)
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(2, state.message.size)
        assertEquals("Something went wrong. Please try again.", state.message.last().text)
        assertFalse(state.message.last().isFromUser)
    }

    @Test
    fun `initial state is empty`() {
        val viewModel = ChatViewModel(
            aiActionService = FakeAIActionService()
        )
        val state = viewModel.uiState.value

        assertEquals("", state.userInput)
        assertEquals(false, state.isLoading)
        assertTrue(state.message.isEmpty())
    }

    @Test
    fun `onSendClick does nothing when input is blank`() = runTest {
        val viewModel = ChatViewModel(
            aiActionService = FakeAIActionService()
        )

        viewModel.onIntent(ChatIntent.InputChanged(" "))

        viewModel.onIntent(ChatIntent.SendClicked)

        val state = viewModel.uiState.value

        assertTrue(state.message.isEmpty())
        assertEquals(" ",state.userInput)
        assertFalse(state.isLoading)
    }

    @Test
    fun `initial state is empty when using default view model constructor`(){
        val viewModel = ChatViewModel(aiActionService = FakeAIActionService())
        val state = viewModel.uiState.value
        assertTrue(state.message.isEmpty())
        assertEquals("",state.userInput)
        assertFalse(state.isLoading)
    }
}