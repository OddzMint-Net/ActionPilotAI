package com.oddzmint.actionpilotai.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.domain.ActionExecutor
import com.oddzmint.actionpilotai.presentation.components.ChatInputBar
import com.oddzmint.actionpilotai.presentation.components.MessageBubble
import androidx.compose.foundation.lazy.items

@Composable
fun ChatScreen(
    viewModel: ChatViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            ChatInputBar(
                value = uiState.userInput,
                onValueChange = viewModel::onInputChange,
                onSendClick = viewModel::onSendClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "ActionPilotAI",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type what you want. I'll turn it into an action.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.message) { messages ->
                    MessageBubble(message = messages, onConfirmAction = { action ->
                        ActionExecutor.execute(
                            context = context,
                            action = action
                        )
                    })
                }
                if (uiState.isLoading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}
