package com.oddzmint.actionpilotai.presentation.chat

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oddzmint.actionpilotai.presentation.designsystem.tokens.Spacing
import com.oddzmint.actionpilotai.ui.theme.ActionPilotAITheme

@Composable
fun ChatHeader() {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ActionPilotAI",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(MaterialTheme.colorScheme.tertiary, CircleShape)
            )
        }
    }
}

@Preview(showBackground = true, name = "Header")
@Composable
private fun ChatHeaderPreview() {
    ActionPilotAITheme {
        ChatHeader()
    }
}

@Preview(showBackground = true, name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChatHeaderDarkPreview() {
    ActionPilotAITheme {
        ChatHeader()
    }
}