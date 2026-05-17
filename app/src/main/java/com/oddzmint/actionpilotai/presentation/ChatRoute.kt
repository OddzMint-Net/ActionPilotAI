package com.oddzmint.actionpilotai.presentation

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oddzmint.actionpilotai.domain.ActionExecutor
import com.oddzmint.actionpilotai.domain.effect.ChatEffect
import com.oddzmint.actionpilotai.domain.intents.ChatIntent

@Composable
fun ChatRoute(
    viewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val voiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val text = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
        if (text != null) {
            viewModel.onIntent(ChatIntent.VoiceInputReceived(text))
        } else {
            viewModel.onIntent(ChatIntent.VoiceInputFailed)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ChatEffect.LaunchVoiceInput -> {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                        )
                        putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your request...")
                    }
                    voiceLauncher.launch(intent)
                }

                is ChatEffect.ExecuteAction -> {
                    ActionExecutor.execute(
                        context = context,
                        action = effect.action
                    )
                }
            }
        }
    }

    ChatScreen(
        uiState = uiState,
        onIntent = viewModel::onIntent
    )
}