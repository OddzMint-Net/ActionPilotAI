package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.repository.AIActionRepository
import com.oddzmint.actionpilotai.domain.usecase.GetAiActionUseCase
import com.oddzmint.actionpilotai.presentation.ChatIntent
import com.oddzmint.actionpilotai.presentation.ChatViewModel
import com.oddzmint.actionpilotai.presentation.ChatViewModel.Companion.ERROR_MESSAGE
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ChatViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeGetAiActionUseCase : GetAiActionUseCase(
        repository = object : AIActionRepository {

            override suspend fun getAction(userInput: String): AIAction {
                return AIAction(
                    type = ActionType.CREATE_EVENT,
                    data = mapOf(
                        "title" to "Team meeting",
                        "date" to "2026-05-10",
                        "time" to "08:30"
                    )
                )
            }
        }
    )

    private class FailingGetAiActionUseCase : GetAiActionUseCase(
        repository = object : AIActionRepository {
            override suspend fun getAction(userInput: String): AIAction {
                throw Exception("Service failed")
            }
        }
    )


    @Test
    fun `onInputChange updates user input`() {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase()
        )
        viewModel.onIntent(ChatIntent.InputChanged("Create meeting tomorrow"))
        assertEquals("Create meeting tomorrow", viewModel.uiState.value.userInput)
    }

    @Test
    fun `onSendClick adds user message and clears input`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase()
        )

        viewModel.onIntent(ChatIntent.InputChanged("Schedule meeting tomorrow"))
        advanceUntilIdle()
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
            getAiActionUseCase = FailingGetAiActionUseCase()
        )
        viewModel.onIntent(ChatIntent.InputChanged("Schedule meeting tomorrow"))
        viewModel.onIntent(ChatIntent.SendClicked)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(2, state.message.size)
        assertEquals(ERROR_MESSAGE, state.message.last().text)
        assertFalse(state.message.last().isFromUser)
    }

    @Test
    fun `initial state is empty`() {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase()
        )
        val state = viewModel.uiState.value

        assertEquals("", state.userInput)
        assertEquals(false, state.isLoading)
        assertTrue(state.message.isEmpty())
    }

    @Test
    fun `onSendClick does nothing when input is blank`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase()
        )

        viewModel.onIntent(ChatIntent.InputChanged(" "))

        viewModel.onIntent(ChatIntent.SendClicked)

        val state = viewModel.uiState.value

        assertTrue(state.message.isEmpty())
        assertEquals(" ", state.userInput)
        assertFalse(state.isLoading)
    }

    @Test
    fun `initial state is empty when using default view model constructor`() {
        val viewModel = ChatViewModel(getAiActionUseCase = FakeGetAiActionUseCase())
        val state = viewModel.uiState.value
        assertTrue(state.message.isEmpty())
        assertEquals("", state.userInput)
        assertFalse(state.isLoading)
    }
}