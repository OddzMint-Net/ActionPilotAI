package com.oddzmint.actionpilotai.presentation.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.oddzmint.actionpilotai.presentation.designsystem.theme.ActionPilotColors
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Elevation
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Spacing

@Composable
fun ChatInputBar(
    value: String,
    isListening: Boolean,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onMicClick: () -> Unit
) {
    Surface(tonalElevation = Elevation.Small)
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type an action...")
                },
                singleLine = true
            )
            IconButton(onClick = onMicClick){
                Icon(
                    imageVector = if (isListening) Icons.Filled.MicOff
                    else
                        Icons.Filled.Mic,
                    contentDescription = if (isListening) "Stop listening" else "Start voice input",
                    tint = if (isListening)
                        ActionPilotColors.Error
                    else
                        ActionPilotColors.Primary
                )
            }

            IconButton(
                onClick = onSendClick,
                enabled = value.isNotBlank() && !isListening
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
}