package com.oddzmint.actionpilotai.presentation.chat

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.presentation.components.ChatInputBar
import com.oddzmint.actionpilotai.presentation.components.MessageBubble
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Spacing
import com.oddzmint.actionpilotai.ui.theme.ActionPilotAITheme

@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onIntent: (ChatIntent) -> Unit
) {
    Scaffold(
        topBar = { ChatHeader() },
        bottomBar = {
            ChatInputBar(
                value = uiState.userInput,
                isListening = uiState.isListening,
                onValueChange = { onIntent(ChatIntent.UpdateInput(it)) },
                onSendClick = { onIntent(ChatIntent.SendMessage) },
                onMicClick = { onIntent(ChatIntent.StartVoiceInput) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(Spacing.Medium)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
            )
            {
                items(uiState.message) { messages ->
                    MessageBubble(
                        message = messages,
                        onConfirmAction = { onIntent(ChatIntent.ConfirmAction(it)) }
                    )
                }

                if (uiState.isLoading) {
                    item {
                        CircularProgressIndicator(modifier = Modifier.size(28.dp))
                    }
                }

                if (uiState.isListening) {
                    item {
                        Text(
                            text = "Listening...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                uiState.error?.let {
                    item {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Chat flow", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChatScreenPreview() {
    ActionPilotAITheme {
        ChatScreen(
            uiState = ChatUiState(
                message = listOf(
                    ChatMessage(text = "Where should I send this and for when?", isFromUser = false),
                    ChatMessage(text = "Set up a meeting with Odwa at the OddzMint offices, 3pm", isFromUser = true),
                    ChatMessage(
                        text = "Got it - one action ready",
                        isFromUser = false,
                        action = AIAction(
                            type = ActionType.CREATE_EVENT,
                            data = mapOf("with" to "Odwa", "where" to "OddzMint office", "time" to "3:00 PM")
                        )
                    )
                )
            ),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty state")
@Composable
private fun ChatScreenEmptyPreview() {
    ActionPilotAITheme {
        ChatScreen(uiState = ChatUiState(), onIntent = {})
    }
}

@Preview(showBackground = true, name = "Loading", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChatScreenLoadingPreview() {
    ActionPilotAITheme {
        ChatScreen(
            uiState = ChatUiState(
                message = listOf(ChatMessage(text = "Set up a meeting with Odwa", isFromUser = true)),
                isLoading = true
            ),
            onIntent = {}
        )
    }
}