/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Radius
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Spacing
import com.oddzmint.actionpilotai.ui.theme.ActionPilotAITheme

@Composable
fun ChatInputBar(
    value: String,
    isListening: Boolean,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onMicClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
        )
        {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = "Type or speak a command",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    IconButton(onClick = onMicClick) {
                        Icon(
                            imageVector = if (isListening) Icons.Filled.MicOff else Icons.Filled.Mic,
                            contentDescription = if (isListening) "Stop listening" else "Start voice input",
                            tint = if (isListening) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(Radius.Pill),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outline
                )
            )

            IconButton(
                onClick = onSendClick,
                enabled = value.isNotBlank() && !isListening,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (value.isNotBlank() && !isListening) {
                            MaterialTheme.colorScheme.secondary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = if (value.isNotBlank() && !isListening) {
                        MaterialTheme.colorScheme.onSecondary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
private fun ChatInputBarPreview() {
    ActionPilotAITheme {
        ChatInputBar(
            value = "",
            isListening = false,
            onValueChange = {},
            onSendClick = {},
            onMicClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With text")
@Composable
private fun ChatInputBarWithTextPreview() {
    ActionPilotAITheme {
        ChatInputBar(
            value = "Set up a meeting with Odwa",
            isListening = false,
            onValueChange = {},
            onSendClick = {},
            onMicClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Listening")
@Composable
private fun ChatInputBarListeningPreview() {
    ActionPilotAITheme {
        ChatInputBar(
            value = "",
            isListening = true,
            onValueChange = {},
            onSendClick = {},
            onMicClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChatInputBarDarkPreview() {
    ActionPilotAITheme {
        ChatInputBar(
            value = "",
            isListening = false,
            onValueChange = {},
            onSendClick = {},
            onMicClick = {}
        )
    }
}