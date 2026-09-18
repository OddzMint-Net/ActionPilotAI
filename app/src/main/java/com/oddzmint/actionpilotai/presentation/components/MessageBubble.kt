package com.oddzmint.actionpilotai.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.presentation.chat.ChatMessage
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Radius
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Spacing
import com.oddzmint.actionpilotai.ui.theme.ActionPilotAITheme

@Composable
fun MessageBubble(
    message: ChatMessage,
    onConfirmAction: (AIAction) -> Unit
) {
    val alignment = if (message.isFromUser) Alignment.End else Alignment.Start
    val bubbleShape = if (message.isFromUser) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 4.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 4.dp, bottomEnd = 16.dp)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    )
    {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .then(
                    if (message.isFromUser) {
                        Modifier.background(MaterialTheme.colorScheme.primary, bubbleShape)
                    } else {
                        Modifier
                            .background(MaterialTheme.colorScheme.surface, bubbleShape)
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), bubbleShape)
                    }
                )
                .padding(Spacing.Medium)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = when {
                    message.isFromUser -> MaterialTheme.colorScheme.onPrimary
                    message.isError -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }
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

@Preview(showBackground = true, name = "User message")
@Composable
private fun MessageBubbleUserPreview() {
    ActionPilotAITheme {
        MessageBubble(
            message = ChatMessage(text = "Set up a meeting with Odwa at the Foreshore office, 3pm", isFromUser = true),
            onConfirmAction = {}
        )
    }
}

@Preview(showBackground = true, name = "Assistant message")
@Composable
private fun MessageBubbleAssistantPreview() {
    ActionPilotAITheme {
        MessageBubble(
            message = ChatMessage(text = "Got it - one action ready.", isFromUser = false),
            onConfirmAction = {}
        )
    }
}

@Preview(showBackground = true, name = "Assistant with action")
@Composable
private fun MessageBubbleWithActionPreview() {
    ActionPilotAITheme {
        MessageBubble(
            message = ChatMessage(
                text = "Got it  one action ready.",
                isFromUser = false,
                action = AIAction(
                    type = ActionType.CREATE_EVENT,
                    data = mapOf("with" to "Odwa", "where" to "Foreshore office", "time" to "3:00 PM")
                )
            ),
            onConfirmAction = {}
        )
    }
}

@Preview(showBackground = true, name = "Error message")
@Composable
private fun MessageBubbleErrorPreview() {
    ActionPilotAITheme {
        MessageBubble(
            message = ChatMessage(text = "No app found to handle this action", isFromUser = false, isError = true),
            onConfirmAction = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MessageBubbleDarkPreview() {
    ActionPilotAITheme {
        MessageBubble(
            message = ChatMessage(
                text = "Got it - one action ready.",
                isFromUser = false,
                action = AIAction(ActionType.CREATE_EVENT, mapOf("with" to "Odwa"))
            ),
            onConfirmAction = {}
        )
    }
}