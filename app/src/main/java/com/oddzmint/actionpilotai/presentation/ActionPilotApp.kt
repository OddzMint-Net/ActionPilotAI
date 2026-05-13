package com.oddzmint.actionpilotai.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ActionPilotApp() {
    val viewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory.Factory)
    ChatScreen(viewModel = viewModel)
}
