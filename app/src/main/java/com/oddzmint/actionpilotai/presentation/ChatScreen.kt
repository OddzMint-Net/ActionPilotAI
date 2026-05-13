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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.oddzmint.actionpilotai.R
import com.oddzmint.actionpilotai.domain.effect.ChatEffect
import com.oddzmint.actionpilotai.domain.intents.ChatIntent
@Composable
fun ChatScreen(
    viewModel: ChatViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ChatEffect.ExecuteAction -> {
                    ActionExecutor.execute(
                        context = context,
                        action = effect.action
                    )
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            ChatInputBar(
                value = uiState.userInput,
                onValueChange = {
                    viewModel.onIntent(
                        ChatIntent.InputChanged(it)
                    )
                },
                onSendClick = { viewModel.onIntent(ChatIntent.SendClicked) }
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
                text = stringResource(R.string.headline_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.sub_text_heading),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.message) { messages ->
                    MessageBubble(
                        message = messages,
                        onConfirmAction = {
                            viewModel.onIntent(
                                ChatIntent.ConfirmAction(it)
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