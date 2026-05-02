package com.oddzmint.actionpilotai.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.R
import com.oddzmint.actionpilotai.data.model.AIAction
import com.oddzmint.actionpilotai.data.model.ActionType

@Composable
fun ActionCard(
    action: AIAction,
    onConfirmClick: (AIAction) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 48.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = getActionTitle(action.type),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            action.data.forEach { (key, value) ->
                Text(
                    text = "${key.replaceFirstChar { it.uppercase() }}:$value",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = stringResource(R.string.confirm),
                onClick = { onConfirmClick(action) }
            )
        }
    }
}

private fun getActionTitle(type: ActionType): String {
    return when (type) {
        ActionType.CREATE_EVENT -> "\uD83D\uDCC5 Create Event"
        ActionType.OPEN_MAPS -> "\uD83D\uDCCD Open Maps"
        ActionType.GENERATE_REPLY -> "\uD83D\uDCAC Generate Reply"
        ActionType.DIAL_PHONE -> "Open Phone Dialer"
        ActionType.SHARE_TEXT -> "Share text"
        ActionType.SEARCH_WEB -> "Search web"
        ActionType.OPEN_URL -> "Open URL"
        ActionType.UNKNOWN -> "❓ Unknown Action"
    }
}