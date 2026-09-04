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
import com.oddzmint.actionpilotai.presentation.chat.ChatMessage
import com.oddzmint.actionpilotai.presentation.designsystem.theme.ActionPilotColors
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Radius
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Spacing

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
                    color = if (message.isFromUser) ActionPilotColors.Primary else ActionPilotColors.Surface,
                    shape = RoundedCornerShape(Radius.Medium)
                )
                .padding(Spacing.Medium)
        )
        {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        message.action?.let { action ->
            Spacer(modifier = Modifier.height(Spacing.Small))
            ActionCard(
                action = action,
                onConfirmClick = onConfirmAction
            )
        }
    }
}