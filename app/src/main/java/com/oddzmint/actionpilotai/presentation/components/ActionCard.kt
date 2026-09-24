/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.R
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Radius
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Spacing
import com.oddzmint.actionpilotai.ui.theme.ActionPilotAITheme

@Composable
fun ActionCard(
    action: AIAction,
    onConfirmClick: (AIAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                RoundedCornerShape(Radius.Medium)
            )
            .padding(Spacing.Medium)
    ) {
        Text(
            text = getActionTitle(action.type),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(Spacing.Medium))
        action.data.forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = key.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
        }
        Spacer(modifier = Modifier.height(Spacing.Small))

        PrimaryButton(
            text = stringResource(R.string.confirm),
            onClick = { onConfirmClick(action) }
        )
    }
}

private fun getActionTitle(type: ActionType): String {
    return when (type) {
        ActionType.CREATE_EVENT -> "\uD83D\uDCC5 Create calendar event"
        ActionType.OPEN_MAPS -> "\uD83D\uDCCD Open Maps"
        ActionType.GENERATE_REPLY -> "\uD83D\uDCAC Generate Reply"
        ActionType.DIAL_PHONE -> "Open Phone Dialer"
        ActionType.SHARE_TEXT -> "Share text"
        ActionType.SEARCH_WEB -> "Search web"
        ActionType.OPEN_URL -> "Open URL"
        ActionType.UNKNOWN -> "❓ Unknown Action"
    }
}

@Preview(showBackground = true, name = "Create event")
@Composable
private fun ActionCardCreateEventPreview() {
    ActionPilotAITheme {
        ActionCard(
            action = AIAction(
                type = ActionType.CREATE_EVENT,
                data = mapOf("with" to "Odwa", "where" to "OddzMint office", "time" to "3:00 PM")
            ),
            onConfirmClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ActionCardDarkPreview() {
    ActionPilotAITheme {
        ActionCard(
            action = AIAction(
                type = ActionType.CREATE_EVENT,
                data = mapOf("with" to "Odwa", "where" to "OddzMint office", "time" to "3:00 PM")
            ),
            onConfirmClick = {}
        )
    }
}