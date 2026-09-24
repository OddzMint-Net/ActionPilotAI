/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.presentation.chat

sealed interface ChatEffect {
    data object LaunchVoiceInput : ChatEffect
}