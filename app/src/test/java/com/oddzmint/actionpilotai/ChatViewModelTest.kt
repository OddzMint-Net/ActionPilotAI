package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.repository.AIActionRepository
import com.oddzmint.actionpilotai.domain.usecase.GetAiActionUseCase
import com.oddzmint.actionpilotai.presentation.chat.effect.ChatEffect
import com.oddzmint.actionpilotai.presentation.chat.intent.ChatIntent
import com.oddzmint.actionpilotai.presentation.chat.reducer.ChatReducer
import com.oddzmint.actionpilotai.presentation.chat.viewmodel.ChatViewModel
import com.oddzmint.actionpilotai.presentation.chat.viewmodel.ChatViewModel.Companion.ERROR_MESSAGE
import kotlinx.coroutines.flow.first
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
        repository = AIActionRepository {
            AIAction(
                type = ActionType.CREATE_EVENT,
                data = mapOf(
                    "title" to "Team meeting",
                    "date" to "2026-05-10",
                    "time" to "08:30"
                )
            )
        }
    )

    private class FailingGetAiActionUseCase : GetAiActionUseCase(
        repository = AIActionRepository { throw Exception("Service failed") }
    )


    @Test
    fun `onInputChange updates user input`() {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.UpdateInput("Create meeting tomorrow"))
        assertEquals("Create meeting tomorrow", viewModel.uiState.value.userInput)
    }

    @Test
    fun `onSendClick adds user message and clears input`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(), reducer = ChatReducer()
        )

        viewModel.onIntent(ChatIntent.UpdateInput("Schedule meeting tomorrow"))
        advanceUntilIdle()
        viewModel.onIntent(ChatIntent.SendMessage)
        val state = viewModel.uiState.value
        assertEquals("", state.userInput)
        assertEquals(2, state.message.size)
        assertEquals("Schedule meeting tomorrow", state.message.first().text)

        assertTrue(state.message.first().isFromUser)
    }

    @Test
    fun `onSendClick shows error message when service fails`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FailingGetAiActionUseCase(), reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.UpdateInput("Schedule meeting tomorrow"))
        viewModel.onIntent(ChatIntent.SendMessage)
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
            getAiActionUseCase = FakeGetAiActionUseCase(), reducer = ChatReducer()
        )
        val state = viewModel.uiState.value

        assertEquals("", state.userInput)
        assertEquals(false, state.isLoading)
        assertTrue(state.message.isEmpty())
    }

    @Test
    fun `onSendClick does nothing when input is blank`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(), reducer = ChatReducer()
        )

        viewModel.onIntent(ChatIntent.UpdateInput(" "))

        viewModel.onIntent(ChatIntent.SendMessage)

        val state = viewModel.uiState.value

        assertTrue(state.message.isEmpty())
        assertEquals(" ", state.userInput)
        assertFalse(state.isLoading)
    }

    @Test
    fun `initial state is empty when using default view model constructor`() {
        val viewModel = ChatViewModel(getAiActionUseCase = FakeGetAiActionUseCase(), reducer = ChatReducer())
        val state = viewModel.uiState.value
        assertTrue(state.message.isEmpty())
        assertEquals("", state.userInput)
        assertFalse(state.isLoading)
    }

    @Test
    fun `onSendClick shows loading state while waiting for AI response`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.UpdateInput("Schedule meeting"))
        viewModel.onIntent(ChatIntent.SendMessage)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
    }

    @Test
    fun `onSendClick adds AI response message on success`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.UpdateInput("Create a meeting"))
        viewModel.onIntent(ChatIntent.SendMessage)
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(2, state.message.size)
        assertFalse(state.message.last().isFromUser)
    }

    @Test
    fun `SubmitVoiceInput adds user message and triggers AI call`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.SubmitVoiceInput("Call Odwa"))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(2, state.message.size)
        assertEquals("Call Odwa", state.message.first().text)
        assertTrue(state.message.first().isFromUser)
    }

    @Test
    fun `SubmitVoiceInput with blank input still adds message`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.SubmitVoiceInput(" "))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(1,state.message.size)
        assertEquals("",state.message.first().text)
    }

    @Test
    fun `StartVoiceInput emits LaunchVoiceInput effect`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.StartVoiceInput)
        advanceUntilIdle()
        val effect = viewModel.effects.first()
        assertEquals(ChatEffect.LaunchVoiceInput, effect)
    }

    @Test
    fun `Confirmation emits ExecuteAction effect`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FakeGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        val action = AIAction(
            type = ActionType.CREATE_EVENT,
            data = mapOf("title" to "Team meeting")
        )
        viewModel.onIntent(ChatIntent.ConfirmAction(action))
        advanceUntilIdle()
        val effect = viewModel.effects.first()
        assertEquals(ChatEffect.ExecuteAction(action), effect)
    }

    @Test
    fun `SubmitVoiceInput shows error message when service fails`() = runTest {
        val viewModel = ChatViewModel(
            getAiActionUseCase = FailingGetAiActionUseCase(),
            reducer = ChatReducer()
        )
        viewModel.onIntent(ChatIntent.SubmitVoiceInput("Book a flight"))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(2, state.message.size)
        assertEquals(ERROR_MESSAGE, state.message.last().text)
        assertFalse(state.message.last().isFromUser)
    }
}