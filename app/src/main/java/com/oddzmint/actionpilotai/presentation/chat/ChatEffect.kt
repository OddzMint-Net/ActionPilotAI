package com.oddzmint.actionpilotai.presentation.chat

sealed interface ChatEffect {
    data object LaunchVoiceInput : ChatEffect
}