package com.oddzmint.actionpilotai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.presentation.chat.model.ChatMessage

@Composable
fun MessageBubble(
    message: ChatMessage,
    onConfirmAction: (AIAction) -> Unit
) {
    val alignment = if (message.isFromUser) Alignment.End else Alignment.Start
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (message.isFromUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(14.dp)
        )
        {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        // Action card (if exists)
        message.action?.let { action ->
            Spacer(modifier = Modifier.height(8.dp))
            ActionCard(
                action = action,
                onConfirmClick = onConfirmAction
            )
        }
    }
}